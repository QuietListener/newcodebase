package andy.com.javaConcurrent.aqs;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 实现一个可以重新如锁
 */
class MyLock {
    private final Sync sync = new Sync();

    public void lock() throws InterruptedException {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    private class Sync extends AbstractQueuedSynchronizer {

        public Sync(){
            setState(0);
        }

        @Override
        protected boolean tryAcquire(int arg) {
            Thread curThread = Thread.currentThread();

            //如果锁以及被当前线程获取
            if(curThread == getExclusiveOwnerThread()){
                setState(getState()+arg);
                return true;
            }else{ //如果还没有获取锁
                int c = getState();
                if(c == 0 && compareAndSetState(0,arg)){
                    setExclusiveOwnerThread(curThread); //把锁设置成当前线程独占
                    return true;
                }
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            Thread curThread = Thread.currentThread();

            if(curThread != getExclusiveOwnerThread()){
                throw new IllegalMonitorStateException("有问题");
            }
            int c = getState();
            int remain = c - arg;
            setState(remain);
            return true;
        }
    }

    public static void main(String[] args) {

        final MyLock lock = new MyLock();

        for (int i = 0; i < 3; i++) {

            final int k = i;
            Thread t = new Thread() {

                @Override
                public void run() {

                    try {

                        System.out.println(getName() + ":" + new Date().getTime() + ": wait for job");
                        lock.lock();
                        lock.lock(); //测试可以重入
                        try {

                            System.out.println(getName() + ":" + new Date().getTime() + ": doing a job");
                            TimeUnit.SECONDS.sleep(3);

                            System.out.println(getName() + ":" + new Date().getTime() + ": finished a job");
                        } finally {
                            lock.unlock();
                            lock.unlock();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            t.setName("t-" + i);
            t.start();
        }


        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("结束");
    }
}
