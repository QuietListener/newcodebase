package andy.com.javaConcurrent;

import java.util.concurrent.TimeUnit;

public class ThreadLocalProfiler {
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<>();

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }


    public static void main(String[] args) {

        for (int i = 1; i < 5; i++) {
            final int time = i;
            Thread t = new Thread() {

                @Override
                public void run() {
                    ThreadLocalProfiler.begin();
                    try {
                        TimeUnit.SECONDS.sleep(time);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("time=" + time + " time consume:" + ThreadLocalProfiler.end() + " ms");
                }
            };
            t.start();

            try {
                t.join();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
