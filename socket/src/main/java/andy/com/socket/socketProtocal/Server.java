package andy.com.socket.socketProtocal;

import andy.com.socket.protocal.Frame;
import andy.com.socket.protocal.FrameNeedMoreBytesException;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 使用非阻塞模式的SocketChannel,ServerSocketChannel.
 */
public class Server {
    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private int port = Common.PORT;
    private Charset charset = Charset.forName("GBK");

    List<Frame> frames = new ArrayList<>;
    byte[] buf = null;

    public Server() throws IOException {
        // 创建一个selector对象
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().setReuseAddress(true);
        // 使serverSocketChannel工作于非阻塞模式
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        System.out.println("服务器启动...");
    }

    public void service() throws IOException {
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int i = selector.select(Common.TIMEOUT);
            if (i == 0) {
                continue;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            while (it.hasNext()) {
                SelectionKey key = null;
                try {
                    key = it.next();
                    it.remove();

                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) key
                                .channel();
                        SocketChannel socketChannel = (SocketChannel) ssc
                                .accept();
                        System.out.println("接收到客户连接，来自："
                                + socketChannel.socket().getInetAddress() + ":"
                                + socketChannel.socket().getPort());
                        socketChannel.configureBlocking(false);
                        FrameBuf fb = new FrameBuf();
                        socketChannel.register(selector, SelectionKey.OP_READ
                                | SelectionKey.OP_WRITE, fb);
                    }

                    if (key.isReadable()) {
                        System.out.println("is readable");
                        receive(key);


                    }

                    if (key.isWritable()) {
                        System.out.println("is writable");
                        send(key);


                    }

                    FrameBuf bf = (FrameBuf) key.attachment();
                    if(bf.getFrameSize() > 0 ){
                        //防止cpu100%
                        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }else{
                        //防止cpu100%
                        key.interestOps(SelectionKey.OP_READ);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        if (key != null) {
                            key.cancel();
                            key.channel().close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


    public void send(SelectionKey key) throws IOException {
        FrameBuf bf = (FrameBuf) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();

        Frame f = bf.getFrame();
        byte [] bs  = f.getData();
        socketChannel.write(ByteBuffer.wrap(bs));
        System.out.println("write "+bs.length +" bytes");
    }

    public void receive(SelectionKey key) throws IOException {
        System.out.println("recieve");
        FrameBuf fb = (FrameBuf) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuff = ByteBuffer.allocate(32);
        socketChannel.read(readBuff);
        readBuff.flip();
        fb.addNewByteData(readBuff.array());
    }

    public String decode(ByteBuffer buffer) {
        CharBuffer charBuffer = charset.decode(buffer);
        return charBuffer.toString();
    }

    public ByteBuffer encode(String str) {
        return charset.encode(str);
    }


    private static class FrameBuf {

        private byte[] buf = new byte[];
        private BlockingQueue<Frame> frames = new LinkedBlockingDeque<>();

        public void addFrame(Frame f) {
            this.frames.add(f);
        }

        public Frame getFrame() {
            return frames.poll();
        }

        public void addNewByteData(byte[] bs) {

            if (bs != null && bs.length > 0) {
                buf = ArrayUtils.addAll(buf, bs);
                try {
                   Frame f = Frame.decode(buf);
                   int length = f.getComprredFrameLength();
                   buf = Arrays.copyOfRange(buf, length, buf.length);
                   System.out.println("new Frame");
                }catch ( FrameNeedMoreBytesException e){
                    System.out.println("need more");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }


        public int getFrameSize(){
            return frames.size();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().service();
    }


}
