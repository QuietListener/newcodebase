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

/**
 * https://kasunpanorama.blogspot.com/2015/04/understanding-reactor-pattern-with-java.html
 */
public class Reactor {

    /**
     * 复用器
     */
    private Selector demultiplexer;

    private ConcurrentHashMap<Integer, EventHandler> handlerMap = new ConcurrentHashMap<>();

    public Reactor() {

    }

    public void run() {
        try {
            while (true) {
                //复用器不断"轮询"事件
                demultiplexer.select();
                Set<SelectionKey> keys = demultiplexer.selectedKeys();
                Iterator<SelectionKey> iterater = keys.iterator();

                while(iterater.hasNext()){
                    SelectionKey key = iterater.next();
                    iterater.remove();

                    //处理事件
                    if(key.isAcceptable()){
                       EventHandler handler = handlerMap.get(SelectionKey.OP_ACCEPT);
                       handler.handle(key);
                    }

                    if(key.isWritable()){
                        EventHandler handler = handlerMap.get(SelectionKey.OP_WRITE);
                        handler.handle(key);
                    }

                    if(key.isReadable()){
                        EventHandler handler = handlerMap.get(SelectionKey.OP_READ);
                        handler.handle(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 复用器
     * @param selector
     */
    public void setDemultiplexer(Selector selector) {
            this.demultiplexer = selector;
    }

    /**
     * 为每一个事件注册一个handler
     * @param event
     * @param handler
     */
    public void registerEventHandler(int event, EventHandler handler) {
        handlerMap.put(event, handler);
    }


    /**
     * 每一个事件一个handler 接口
     */
    private static interface EventHandler {
        void handle(SelectionKey key);
    }


    /**
     * 处理Accept事件的Handler
     */
    static class AcceptEventHandler implements EventHandler {


        @Override
        public void handle(SelectionKey key) {
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = (SocketChannel) ssc.accept();

                System.out.println("from："
                        + sc.socket().getInetAddress() + ":"
                        + sc.socket().getPort());

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                sc.configureBlocking(false);
                sc.register(key.selector(),SelectionKey.OP_READ,buffer);
                key.selector().wakeup();

            }catch (IOException e){
                e.printStackTrace();
                key.cancel();
            }

        }
    }


    /**
     * 处理Read事件的Handler
     */
    static class ReadEventHandler implements EventHandler {

        @Override
        public void handle(SelectionKey key) {
            try {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer bf = (ByteBuffer)key.attachment();
                int count = sc.read(bf);
                System.out.println("read :"+count);
                if(count < 0 ){
                    key.cancel();
                }

                if(count>0){
                    key.interestOps(SelectionKey.OP_WRITE);
                }

            }catch (Exception e){
                e.printStackTrace();
                key.cancel();
            }

        }
    }


    /**
     * 处理Write事件的Handler
     */
    static class WriteEventHandler implements EventHandler {

        @Override
        public void handle(SelectionKey key) {
            try {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer bf = (ByteBuffer) key.attachment();

                bf.flip();
                while (bf.hasRemaining()) {
                   sc.write(bf);
                }

                System.out.println("read :"+bf.limit());
                bf.clear();
                key.interestOps(SelectionKey.OP_READ);

            }catch (IOException e){
                e.printStackTrace();
                key.cancel();
            }

        }
    }




    public static void main(String [] args) throws Exception {
        ServerSocketChannel ssc =  ServerSocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress("0.0.0.0",10000);
        ssc.bind(addr);
        ssc.configureBlocking(false);
        Selector selector =  Selector.open();
        ssc.register(selector,SelectionKey.OP_ACCEPT);
        System.out.println("reactor is started : listen at :"+addr);

        Reactor reactor = new Reactor();
        reactor.setDemultiplexer(selector);
        reactor.registerEventHandler(SelectionKey.OP_READ,new ReadEventHandler());
        reactor.registerEventHandler(SelectionKey.OP_WRITE,new WriteEventHandler());
        reactor.registerEventHandler(SelectionKey.OP_ACCEPT,new AcceptEventHandler());
        reactor.run();;
    }

}



