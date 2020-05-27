package andy.com.internet.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 模拟redisson分布式锁，带watchdog。不支持可重入，不支持trylock的超时时间~
 * redission在redis宕机的时候lock会死锁(不会抛异常),redis宕机再起来，lock也回死锁。
 * 这个分布式锁，redis宕机，或者连接断开，会立刻抛异常。
 */
public class RedisDistributeLock {

    public static int DefaultExpireMs = 1000 * 10 * 3;
    public static int MaxRenew = 1000 * 60 * 3;

    private JedisPool pool = null;
    private static String OK = "OK";
    private static String UnlockOK = "1";

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private Map<String, Long> currKeyValues = new HashMap<>();
    private WatchDog watchDog;

    public RedisDistributeLock(String host, int port, int maxTotal, int maxIdle, int poolMaxWait, int timeout, boolean testOnBorrow, String password) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(poolMaxWait);
        config.setTestOnBorrow(testOnBorrow);
        if (timeout < 0) {
            timeout = 200;
        }

        pool = new JedisPool(config, host, port, timeout, password);
        watchDog = new WatchDog();
        watchDog.setName("WatchDog");
        watchDog.setDaemon(true);
        watchDog.start();

    }

    public boolean lock(String key, String value, int expireMs) {
        return this.lock(key, value, expireMs, false);
    }


    public boolean lock(String key, String value) {
        return this.lock(key, value, DefaultExpireMs, true);
    }


    public boolean unLock(String key, String value) {
        String script = "if  redis.call('get', KEYS[1]) == ARGV[1] " +
                "then " +
                "   return redis.call('del', KEYS[1])" +
                "else" +
                "   return 0 " +
                "end";

        writeLock.lock();
        try {
            currKeyValues.remove(key, value);
        } finally {
            writeLock.unlock();
        }

        int tryCount = 0;
        while (tryCount < 3) {
            try (Jedis jedis = pool.getResource()) {
                Object ret = jedis.eval(script, Arrays.asList(key), Arrays.asList(value));
                if (UnlockOK.equals(ret + "")) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            tryCount += 1;
        }

        return false;
    }

    private boolean renew(String key, int expireS) {
        int tryCount = 0;
        while (tryCount < 3) {
            try (Jedis jedis = pool.getResource()) {
                long ret = jedis.expire(key, expireS);
                return ret == 1l;
            } catch (Exception e) {
                e.printStackTrace();
            }
            tryCount += 1;
        }
        return false;
    }

    private boolean lock(String key, String value, int expireMs, boolean usingWatchDog) {

        try (Jedis jedis = pool.getResource()) {
            String ret = jedis.set(key, value, "NX", "PX", expireMs);
            if (OK.equals(ret)) {
                if (usingWatchDog) {
                    writeLock.lock();
                    try {
                        currKeyValues.put(key, System.currentTimeMillis());
                    } finally {
                        writeLock.unlock();
                    }
                }
                return true;
            }
            return false;
        }
    }


    private class WatchDog extends Thread {

        @Override
        public void run() {

            String name = Thread.currentThread().getName();
            while (true) {
                try {
                    System.out.println(name + " start");
                    boolean locked = readLock.tryLock(1, TimeUnit.SECONDS);
                    List<String> toRemovedKey = new ArrayList<>();
                    if (locked) {
                        try {
                            for (Map.Entry<String, Long> entry : currKeyValues.entrySet()) {
                                String key = entry.getKey();
                                long startAt = entry.getValue();
                                if (System.currentTimeMillis() - startAt > MaxRenew) {
                                    toRemovedKey.add(key);
                                    continue;
                                }
                                boolean ret = renew(key, DefaultExpireMs / 1000);
                                System.out.println(name + " renew " + key + " " + ret);
                                if (ret == false) {
                                    toRemovedKey.add(key);
                                }

                            }
                        } finally {
                            readLock.unlock();
                        }
                    }

                    boolean wlocked = writeLock.tryLock(1, TimeUnit.SECONDS);
                    if (wlocked) {
                        try {
                            for (String key : toRemovedKey) {
                                currKeyValues.remove(key);
                                System.out.println(name + " remove " + key + " ");
                            }
                        } finally {
                            writeLock.unlock();
                        }
                    }

                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Throwable e) {
                    e.printStackTrace();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }
            }
        }
    }


    static public void test1() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 50, 100, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1000), new ThreadPoolExecutor.DiscardPolicy());


        RedisDistributeLock lock = new RedisDistributeLock("localhost", 6379, 2, 1, 500, 200, true, "123456");


        final String key = "dkey";
        for (int i = 0; i < 1000; i++) {
            int j = i;
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    int sleepMs = new Random().nextInt(4);
                    try {
                        TimeUnit.SECONDS.sleep(sleepMs);
                    } catch (Exception e) {

                    }
                    int sleepTime = 1000 * new Random().nextInt(10);
                    boolean locked = false;
                    String value = Thread.currentThread().getName() + System.currentTimeMillis();
                    try {
                        locked = lock.lock(key, value);
                        if (locked) {
                            System.out.println("#" + j + ":locked and do stuff using " + sleepTime + " Ms");
                            TimeUnit.MILLISECONDS.sleep(sleepTime);
                        } else {
                            System.out.println(j + ":locked failed");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (locked == true) {
                            boolean ret = lock.unLock(key, value);
                            System.out.println("#" + j + ":unlocked :" + ret + " using " + sleepTime + " Ms");
                        }
                    }
                }
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(1000, TimeUnit.SECONDS);
        } catch (Exception e) {
        }
        pool.shutdownNow();
    }

    public static void testWatchDog() throws Exception {

        RedisDistributeLock lock = new RedisDistributeLock("localhost", 6379, 2, 1, 500, 200, true, "123456");
        String key = "kk";
        String value = "value";
        boolean locked = lock.lock(key, value);
        System.out.println("1locked=" + locked);
        locked = lock.lock(key, value);
        System.out.println("2locked=" + locked);

        TimeUnit.MILLISECONDS.sleep(10 * 1000);
        boolean unlock = lock.unLock(key, value);
        System.out.println("unlocked=" + unlock);

        locked = lock.lock(key, value);
        System.out.println("3locked=" + locked);
        lock.unLock(key, value);

    }


    public static void main(String[] args) throws Exception {
        //testWatchDog();
        test1();
    }

}
