package andy.com.internet.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 还没写好
 * https://kasunpanorama.blogspot.com/2015/04/understanding-reactor-pattern-with-java.html
 */
public class MultiThreadReactor {

    /**
     * 复用器
     */
    private Selector demultiplexer;

    private ConcurrentHashMap<Integer, EventHandler> handlerMap = new ConcurrentHashMap<>();

    public MultiThreadReactor() {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            InetSocketAddress addr = new InetSocketAddress("0.0.0.0", 10000);
            ssc.bind(addr);
            ssc.configureBlocking(false);
            demultiplexer = Selector.open();
            ssc.register(demultiplexer, SelectionKey.OP_ACCEPT);
            System.out.println("reactor is started : listen at :" + addr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                //复用器不断"轮询"事件
                demultiplexer.select();
                Set<SelectionKey> keys = demultiplexer.selectedKeys();
                Iterator<SelectionKey> iterater = keys.iterator();

                while (iterater.hasNext()) {
                    SelectionKey key = iterater.next();
                    iterater.remove();

                    try {
                        if (key.isAcceptable()) {
                            SocketChannel sc = null;
                            try {
                                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                                sc = (SocketChannel) ssc.accept();

                                System.out.println("from："
                                        + sc.socket().getInetAddress() + ":"
                                        + sc.socket().getPort());
                                sc.configureBlocking(false);
                                sc.register(key.selector(), SelectionKey.OP_READ, new EventHandler(sc, demultiplexer));
                                key.selector().wakeup();

                            } catch (IOException e) {
                                e.printStackTrace();
                                try {
                                    key.cancel();
                                    sc.close();
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                            }
                        } else {
                            dispatch(key);
                        }
                    }catch (Exception e3){
                        try {
                            key.cancel();
                        }catch (Exception e){
                            e3.printStackTrace();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispatch(SelectionKey key) {

        Runnable run = (Runnable) key.attachment();
        if(run != null){
            run.run();
        }
    }


    static class EventHandler implements Runnable {

        SocketChannel channel;
        Selector selector;
        ByteBuffer bf = ByteBuffer.allocate(1024);

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        public EventHandler(SocketChannel channel, Selector selector) {
            this.selector = selector;
            this.channel = channel;
        }

        @Override
        public void run() {
            SelectionKey key = channel.keyFor(selector);
            try {

                if (key.isReadable()) {
                    pool.submit(() -> read(key));
                    key.interestOps(SelectionKey.OP_WRITE);
                }else if (key.isWritable()) {
                    pool.submit(() -> write(key));
                    key.interestOps(SelectionKey.OP_READ);
                }

            }catch (Exception e){
                e.printStackTrace();

                try {
                    key.cancel();
                    channel.close();
                }catch (Exception e2){
                    e2.printStackTrace();;
                }
            }
        };

        private void read(SelectionKey key ) {
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                int count = sc.read(bf);
                System.out.println(Thread.currentThread().getName()+" read :" + count);
                if (count <= 0) {
                    throw new Exception("read -1");
                }

            } catch (Exception e) {
                e.printStackTrace();

                try {
                    key.cancel();
                    if(channel != null)
                        channel.close();
                }catch (Exception e2){
                    e2.printStackTrace();;
                }
            }
        }

        private void write(SelectionKey key) {
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                bf.flip();
                while (bf.hasRemaining()) {
                    sc.write(bf);
                }

                System.out.println(Thread.currentThread().getName()+" write :" + bf.limit());
                bf.clear();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    key.cancel();
                    if(channel != null)
                        channel.close();
                }catch (Exception e2){
                    e2.printStackTrace();;
                }
            }

        }
    }


    public static void main(String[] args) throws Exception {
        MultiThreadReactor reactor = new MultiThreadReactor();
        reactor.run();
    }

}



