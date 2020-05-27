package andy.com.internet.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

//每个链接一个线程来处理
public class ThreadPerConnEchoServer {

    private int port;
    private ServerSocket serverSocket;

    public ThreadPerConnEchoServer(int port) {
        this.port = port;
    }

    void start() throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("0.0.0.0",10000));
        System.out.println("Server started and listen at :" + port);
        int count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            Socket socket = serverSocket.accept();
            //每个链接一个线程来处理
            new EchoHandler("EchoHandler-" + count++, socket).start();
        }
    }

    private class EchoHandler extends Thread {

        private String name = null;
        private Socket socket = null;

        public EchoHandler(String name, Socket socket) {
            this.name = name;
            this.setName(name);
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println(name + " start");
            while (!socket.isClosed() && !Thread.currentThread().isInterrupted()) {
                try {

                    InputStream inputStream = this.socket.getInputStream();
                    byte[] buff = new byte[1024];
                    int count = inputStream.read(buff);
                    System.out.println(name + " read "+ count);
                    if (count > 0) {

                        //业务逻辑
                        process(buff);

                        socket.getOutputStream().write(buff, 0, count);
                    }

                    /**
                     * 链接已经断掉了~
                     */
                    if(count < 0){
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println(name + " end");
        }
    }

    //业务逻辑
    private void process(byte [] data) throws Exception{
        TimeUnit.MILLISECONDS.sleep(100);
    }

    public static void main(String[] args) throws Exception {
        ThreadPerConnEchoServer server = new ThreadPerConnEchoServer(10000);
        server.start();
    }
}
