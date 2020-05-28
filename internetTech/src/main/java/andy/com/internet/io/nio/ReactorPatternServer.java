package andy.com.internet.io.nio;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ReactorPatternServer {

    private Selector demultiplexer;

    private ConcurrentHashMap<Integer, EventHandler> handlerMap = new ConcurrentHashMap<>();

    public ReactorPatternServer() {

    }

    public void run() {
        try {
            while (true) {
                demultiplexer.select();
                Set<SelectionKey> keys = demultiplexer.selectedKeys();
                Iterator<SelectionKey> iterater = keys.iterator();
                while(iterater.hasNext()){
                    SelectionKey key = iterater.next();

                    if(key.isAcceptable()){
                       EventHandler handler = handlerMap.get(SelectionKey.OP_ACCEPT);
                       handler.handle(key);
                    }

                    if(key.isWritable()){
                        EventHandler handler = handlerMap.get(SelectionKey.OP_WRITE);
                        handler.handle(key);
                    }

                    if(key.isReadable()){
                        EventHandler handler = handlerMap.get(SelectionKey.OP_WRITE);
                        handler.handle(key);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDemultiplexer(Selector selector) {

    }

    public void RegisterEventHandler(int event, EventHandler handler) {
        handlerMap.put(event, handler);
    }


    private static interface EventHandler {
        void handle(SelectionKey key);
    }

    static class AcceptEventHandler implements EventHandler {

        @Override
        public void handle(SelectionKey key) {

        }
    }

    static class ReadEventHandler implements EventHandler {

        @Override
        public void handle(SelectionKey key) {

        }
    }

    static class WriteEventHandler implements EventHandler {

        @Override
        public void handle(SelectionKey key) {

        }
    }


}



