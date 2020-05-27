package andy.com.internet.io;

import org.junit.Test;

import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ServerTest {

    private class EchoTask extends Thread {

        private String name;
        private String content;
        private int writeCount = 1;

        public EchoTask(String name, String content, int writeCount) {
            this.name = name;
            this.content = content;
            this.writeCount = writeCount;
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
                }
            }

        }
    }


    @Test
    public void test() throws Exception {
        for (int i = 0; i < 100; i++) {
            String name = "Socket" + i;
            Thread t = new EchoTask(name, name, i % 2 + 1);
            t.start();
        }

        TimeUnit.SECONDS.sleep(10);

    }
}
