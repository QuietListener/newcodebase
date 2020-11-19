package andy.com.javaConcurrent;

public class StopThread {

    public static void main(String [] args){


    }


    /**
     * 优雅地停止线程
     * interrupt和cannel都可以
     */
    public static class Runner1 implements Runnable{

        private long i = 0;
        private volatile  boolean on = true;

        @Override
        public void run() {
            while(on && !Thread.currentThread().isInterrupted()){
                ThreadLocal t =null;
                t.get()
                i++;
            }

            System.out.println("count i="+i);
        }

        public void cannel(){
            this.on = false;
        }
    }
}
