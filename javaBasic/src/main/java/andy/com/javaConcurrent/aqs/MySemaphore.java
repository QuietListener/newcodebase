package andy.com.javaConcurrent.aqs;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自己自己实现一个信号量
 */
public class MySemaphore {

    private volatile Sync sync = null;

    public MySemaphore(int count) {
        sync = new Sync(count);
    }

    public void acquire() {
        sync.acquireShared(1);
    }

    public boolean tryAcquire(TimeUnit unit, long time) throws  InterruptedException{
        return sync.tryAcquireSharedNanos(1,unit.toNanos(time));
    }

    public void release() {
        sync.releaseShared(1);
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            this.setState(count);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            while (true) {
                int current_count = this.getState();
                if (compareAndSetState(current_count, current_count + arg)) {
                    return true;
                }
            }
        }


        @Override
        protected int tryAcquireShared(int arg) {
            while (true) {
                int current_count = this.getState();
                int remains = current_count - arg;
                if (remains < 0) {
                    return remains;
                }
                if (this.compareAndSetState(current_count, remains)) {
                    return remains;
                }
            }

        }
    }


    public static void main(String[] args) {
        MySemaphore s = new MySemaphore(2);

        //Semaphore s = new Semaphore(2);
        for (int i = 0; i < 5; i++) {

            final int k = i;
            Thread t = new Thread() {

                @Override
                public void run(){

                    try {
                        boolean isOk = s.tryAcquire(TimeUnit.SECONDS, k + 2);
                        if (isOk) {
                            try {

                                System.out.println(getName() + ":" + new Date().getTime() + ": acquire a permit");
                                try {
                                    System.out.println(getName() + ":" + new Date().getTime() + ": doing a job");
                                    TimeUnit.SECONDS.sleep(5);
                                    System.out.println(getName() + ":" + new Date().getTime() + ": finished a job");
                                } finally {
                                    s.release();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println(getName() + ":" + new Date().getTime() + ": do not get a permit");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            t.setName("t-" + i);
            t.start();
        }


        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("结束");

    }
}
