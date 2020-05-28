package andy.com.internet.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.*;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.*;
import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * https://www.ibm.com/developerworks/cn/java/j-lo-apachethrift/
 * https://juejin.im/post/5b290e225188252d9548fe15
 *
 */
public class MultiplicationServer {

	public static final int PORT = 19090;

	public static TProcessor getProcessor(){
		MultiplicationHandler handler = new MultiplicationHandler();
		TProcessor processor = new MultiplicationService.Processor<MultiplicationService.Iface>(handler);
		return processor;
	}

	@Test
	public void testTThreadedSelectorServer() {

		try {
			TProcessor processor = getProcessor();
			TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(PORT); //传输层

			TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
			tArgs.protocolFactory(new TBinaryProtocol.Factory()); //协议
			tArgs.maxReadBufferBytes = Integer.MAX_VALUE;
			tArgs.selectorThreads(1);
			tArgs.workerThreads(10);
			tArgs.processor(processor);


			TServer server = new TThreadedSelectorServer(tArgs);

			System.out.println("Starting the TThreadedSelectorServer server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Test
	public void testTSimpleServer() throws Exception{

		try {
			TProcessor processor = getProcessor();

			TServerSocket serverTransport = new TServerSocket(new ServerSocket(PORT)); //transport
			TSimpleServer.Args tArgs = new TSimpleServer.Args(serverTransport);
			tArgs.processor(processor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory()); //协议
			TServer server = new TSimpleServer(tArgs);

			System.out.println("Starting the SimpleServer ...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
