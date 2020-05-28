package andy.com.internet.io;

import org.junit.Test;

import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTest {

    private class EchoTask extends Thread {

        private String name;
        private String content;
        private int writeCount = 1;
        private CountDownLatch latch;

        public EchoTask(String name, String content, int writeCount, CountDownLatch latch) {
            this.name = name;
            this.content = content;
            this.writeCount = writeCount;
            this.latch = latch;
        }

        @Override
        public void run() {
            Socket socket = null;
            try {
                long start = System.currentTimeMillis();
                //Socket socket = new Socket();
                socket = new Socket("127.0.0.1", 10000);
                for (int i = 0; i < writeCount; i++) {
                    socket.getOutputStream().write(content.getBytes());
                    byte[] buff = new byte[1024];
                    int count = socket.getInputStream().read(buff);
                    String echo = null;
                    if (count > 0) {
                        echo = new String(Arrays.copyOfRange(buff, 0, count));
                        long cost = System.currentTimeMillis() - start;
                        System.out.println(name + "_" + i + " cost:" + cost + "," + echo);
                    }

                    assert echo != null && echo.equals(this.content);

                    if (count < 0) {
                        socket.close();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            } finally {
                try {
                    socket.close();
                }catch (Exception ee){
                    ee.printStackTrace();
                }
                latch.countDown();
            }
        }
    }


    @Test
    public void test() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        int count = 1000;
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            String name = "Socket" + i;
            Thread t = new EchoTask(name, name, i % 5 + 1, countDownLatch);
            pool.submit(t);
        }

        countDownLatch.await();
        long cost = System.currentTimeMillis() - start;
        System.out.println("cost = " + cost + " count = " + count + " qps=" +   count/(cost*1.0/1000));
    }
}
