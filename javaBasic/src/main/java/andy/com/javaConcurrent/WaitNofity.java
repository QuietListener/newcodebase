package andy.com.javaConcurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNofity {

    private static  Object lock = new Object();
    private static boolean flag = true;

    public static void main(String [] args) throws Exception {
        new WaitThread().start();
        TimeUnit.SECONDS.sleep(1);
        new NofityThread().start();
    }

    static class WaitThread extends Thread {

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    while (flag == true) {
                        System.out.println(getDate() + " wait flag = " + flag);
                        lock.wait();
                    }

                    System.out.println(getDate() + " do job flag = " + flag);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class NofityThread extends Thread {

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println(getDate() + " hold lock flag = " + flag);
                    lock.notify();
                    flag = false;
                    TimeUnit.SECONDS.sleep(5);
                }

                TimeUnit.MILLISECONDS.sleep(5);

                synchronized (lock) {
                    System.out.println(getDate() + " hold lock again flag = " + flag);
                    TimeUnit.SECONDS.sleep(5);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String getDate() {
        return Thread.currentThread().getName() + " : " + new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
