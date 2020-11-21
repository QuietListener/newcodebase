package andy.com.javaConcurrent.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class MySemaphore {

    private volatile Sync sync = null;
    public MySemaphore(int count){
        sync = new Sync(count);
    }

    public boolean tryAcquire(int c){
        return true;
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            this.setState(count);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            while(true){
                int current_count = this.getState();
                if(compareAndSetState(current_count,current_count+arg)){
                    return true;
                }
            }
        }


        @Override
        protected int tryAcquireShared(int arg) {
            while(true) {
                int current_count = this.getState();
                int remains = current_count - arg;
                if (remains <= 0) {
                    return remains;
                }
                boolean ret = this.compareAndSetState(current_count, remains);
                if(ret)
                return remains;
            }
        }
    }
}
