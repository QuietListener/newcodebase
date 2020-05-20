package andy.com.internet.lock;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RedissionTest {


    public RedissonClient getRedissionClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();

        singleServerConfig.setAddress("redis://127.0.0.1:6379")
        .setConnectionMinimumIdleSize(2)
        .setConnectionPoolSize(10)
        //.setKeepAlive(true)
        .setTimeout(300)
        .setPassword("123456")
        .setDatabase(0)
        .setPingConnectionInterval(2000)
        .setRetryAttempts(1)
        .setRetryInterval(0);


        config.setLockWatchdogTimeout(10 * 1000);
        //config.setKeepPubSubOrder(true);

        RedissonClient client = Redisson.create(config);
        return client;

    }


    @Test
    public void test1() throws Exception {

        RedissonClient client = getRedissionClient();
        String Key1 = "key1";
        RBucket<String> b1 = client.getBucket(Key1);
        b1.setAsync("121212", 10, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(1);

        RBucket<String> b2 = client.getBucket(Key1);
        String value1 = b2.get();
        System.out.print("value1 = " + value1 + "\r\n");

        RBucket<String> b3 = client.getBucket("kkd");
        String value2 = b3.get();
        System.out.print("value2 = " + value2 + "\r\n");

        System.out.println();
        client.shutdown();

    }

    /**
     * 使用不带leaseTime的lock方法，启动看门狗自动续期功能
     *
     * @throws Exception
     */
    @Test
    public void testLockWithoutLeaseTime() throws Exception {
        final String lockName = "lock";

        Thread t1 = new RedissionWithoutLeaseTimeThread(getRedissionClient(), lockName, 12);
        t1.setName("t1");
        t1.start();

        Thread t2 = new RedissionWithoutLeaseTimeThread(getRedissionClient(), lockName, 2);
        t2.setName("t2");
        TimeUnit.SECONDS.sleep(2);
        t2.start();
        ;

        System.out.println("end1");
        //t1.join();
        System.out.println("end2");
        //t2.join();
        System.out.println("end");
        TimeUnit.SECONDS.sleep(20);

    }


    /**
     * 使用带leaseTime的lock方法，不会启动看门狗自动续期功能，到期后key就会失效 释放锁，慎用
     * <p>
     * 例如下面的例子
     * 运行结果如下
     * <p>
     * Wed May 20 17:13:32 CST 2020  t1 get lock sleep 12 second  #t1获取锁后 会执行12秒
     * Wed May 20 17:13:36 CST 2020  t2 do not get  lock sleep #5秒后锁过期，释放
     * Wed May 20 17:13:37 CST 2020  t2 get lock sleep 2 second #t2获取锁
     * Wed May 20 17:13:39 CST 2020  t2 unlock #t2释放锁
     * Wed May 20 17:13:44 CST 2020  t1 unlock #t1释放锁出现错误
     * <p>
     * java.lang.IllegalMonitorStateException: attempt to unlock lock, not locked by current thread by node id: 3f95a47c-32f8-4cd9-921b-19f1be1787d4 thread-id: 23
     * at org.redisson.RedissonLock.lambda$unlockAsync$3(RedissonLock.java:601)
     *
     * @throws Exception
     */
    @Test
    public void testLockWithLeaseTime() throws Exception {


        final String lockName = "lock";

        Thread t1 = new RedissionWithLeaseTimeThread(getRedissionClient(), lockName, 12, 5);
        t1.setName("t1");
        t1.start();

        Thread t2 = new RedissionWithLeaseTimeThread(getRedissionClient(), lockName, 2, 5);
        t2.setName("t2");
        TimeUnit.SECONDS.sleep(1);
        t2.start();

        t1.join();
        t2.join();

    }

    /**
     * 使用不带 leaseTime的lock方法，会启动看门狗自动续期功能
     */
    public static class RedissionWithoutLeaseTimeThread extends Thread {

        private String lockName = "lockDefatult";
        private RedissonClient client = null;
        private long sleepS = 30;

        public RedissionWithoutLeaseTimeThread(RedissonClient client, String lockName, long sleepS) {
            this.client = client;
            this.lockName = lockName;
            this.sleepS = sleepS;
        }

        @Override
        public void run() {

            try {
                while (true) {
                    RLock lock1 = client.getLock(lockName);

                    //使用不带 leaseTime的lock方法，会启动看门狗自动续期功能
                    System.out.println(new Date() + "  " + Thread.currentThread().getName() + " trylock");
                    //boolean res = lock1.tryLock(1, TimeUnit.SECONDS);
                    boolean res = true;
                    lock1.lockInterruptibly();
                    if (res) {
                        try {
                            System.out.println(new Date() + "  " + Thread.currentThread().getName() + " get lock sleep " + sleepS + " second");
                            TimeUnit.SECONDS.sleep(sleepS);
                        } finally {
                            System.out.println(new Date() + "  " + Thread.currentThread().getName() + " unlock");
                            lock1.unlock();
                            System.out.println(new Date() + "  " + Thread.currentThread().getName() + " unlocked");
                            break;
                        }
                    } else {
                        System.out.println(new Date() + "  " + Thread.currentThread().getName() + " do not get  lock sleep");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.client.shutdown();
            }
        }

    }


    @Test
    public void testLock1() throws Exception {


        final String lockName = "lock";

        Thread t1 = new RedissionWithLeaseTimeThread(getRedissionClient(), lockName, 4, 5);
        t1.setName("t1");
        t1.start();

        Thread t2 = new RedissionWithLeaseTimeThread(getRedissionClient(), lockName, 4, 5);
        t2.setName("t2");
        TimeUnit.SECONDS.sleep(1);
        t2.start();

        t1.join();
        t2.join();

    }

    /**
     * 使用带leaseTime的lock方法，不会启动看门狗自动续期功能，到期后key就会失效，慎用
     */
    public static class RedissionWithLeaseTimeThread extends Thread {

        private String lockName = "lockDefatult";
        private RedissonClient client = null;
        private long sleepS = 30;
        private int leaseTimeS = 30;

        public RedissionWithLeaseTimeThread(RedissonClient client, String lockName, long sleepS, int leaseTimeS) {
            this.client = client;
            this.lockName = lockName;
            this.sleepS = sleepS;
            this.leaseTimeS = leaseTimeS;
        }

        @Override
        public void run() {

            try {
                while (true) {
                    RLock lock1 = client.getLock(lockName);

                    //使用带leaseTime的lock方法，不会启动看门狗自动续期功能，到期后key就会失效释放锁，这里最多锁住10ms
                    System.out.println(new Date() + "  " + Thread.currentThread().getName() + " trylock");
                    boolean res = lock1.tryLock(2, this.leaseTimeS, TimeUnit.SECONDS);
                    if (res) {
                        try {
                            System.out.println(new Date() + "  " + Thread.currentThread().getName() + " get lock sleep " + sleepS + " second");
                            TimeUnit.SECONDS.sleep(sleepS);
                        } finally {
                            System.out.println(new Date() + "  " + Thread.currentThread().getName() + " unlock");
                            lock1.unlock();
                            break;
                        }
                    } else {
                        System.out.println(new Date() + "  " + Thread.currentThread().getName() + " do not get  lock sleep");
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.client.shutdown();
            }
        }

    }


}
