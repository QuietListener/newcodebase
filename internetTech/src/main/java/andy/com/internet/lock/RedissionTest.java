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

    @Test
    public void test1() throws Exception {

        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://127.0.0.1:6379");
        singleServerConfig.setConnectionMinimumIdleSize(2);
        singleServerConfig.setConnectionPoolSize(10);
        singleServerConfig.setKeepAlive(true);
        singleServerConfig.setTimeout(100);
        singleServerConfig.setPassword("123456");
        singleServerConfig.setDatabase(0);
        config.setLockWatchdogTimeout(5);

        final RedissonClient client = Redisson.create(config);

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

    @Test
    public void testLock() throws Exception {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://127.0.0.1:6379");
        singleServerConfig.setConnectionMinimumIdleSize(2);
        singleServerConfig.setConnectionPoolSize(10);
        singleServerConfig.setKeepAlive(true);
        singleServerConfig.setTimeout(100);
        singleServerConfig.setPassword("123456");
        singleServerConfig.setDatabase(0);
        config.setLockWatchdogTimeout(5);

        final RedissonClient client = Redisson.create(config);

        final String lockName = "lock";


        Thread t1 = new Thread() {

            @Override
            public void run() {
                try {
                    while (true) {
                        RLock lock1 = client.getLock(lockName);
                        boolean res = lock1.tryLock();
                        if (res) {
                            try {
                                System.out.println(new Date() + "  " + Thread.currentThread().getName() + " get lock sleep 15 second");
                                TimeUnit.SECONDS.sleep(15);
                            } finally {
                                System.out.println(new Date() + "  " + Thread.currentThread().getName() + " unlock");
                                lock1.unlock();
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread() {

            @Override
            public void run() {

                try {
                    while (true) {
                        RLock lock1 = client.getLock(lockName);
                        boolean res = lock1.tryLock();
                        if (res) {
                            try {
                                System.out.println(new Date() + "  " + Thread.currentThread().getName() + " get lock sleep 3 second");
                                TimeUnit.SECONDS.sleep(3);
                            } finally {
                                System.out.println(new Date() + "  " + Thread.currentThread().getName() + " unlock");
                                lock1.unlock();
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        TimeUnit.SECONDS.sleep(2);
        t2.setName("t2");
        t2.start();

        TimeUnit.SECONDS.sleep(19);
        client.shutdown();

    }


}
