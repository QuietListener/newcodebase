package andy.com.javaConcurrent.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class printInTurn {

    public static volatile int count = 0;
    public static final Object lock = new Object();

    static  class ThreadInTurn extends Thread{
        private int pos = 0;
        public ThreadInTurn(int pos){
            this.pos = pos;
        }

        @Override
        public void run(){
            try {
                synchronized (lock) {
                    while (true) {
                        while (count % 3 != pos) {
                            lock.wait();
                        }

                        System.out.print(this.pos);

                        count++;
                        TimeUnit.SECONDS.sleep(2);
                        lock.notifyAll();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

   public static void main(String [] args){
        for(int i = 0;i < 3;i++){
            new ThreadInTurn(i).start();
        }
        try {

            Thread.currentThread().join();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
