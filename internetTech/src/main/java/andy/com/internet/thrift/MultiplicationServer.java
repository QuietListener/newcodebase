package andy.com.internet.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.*;
import org.apache.thrift.server.*;
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


	/**
	 * 阻塞io 单线程 一次处理一个请求
	 * @throws Exception
	 */
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


	/**
	 * 阻塞io 单线程 一次处理一个请求
	 * @throws Exception
	 */
	@Test
	public void testThreadPoolServer() throws Exception{

		try {
			TProcessor processor = getProcessor();
			TServerSocket serverTransport = new TServerSocket(new ServerSocket(PORT)); //transport
			TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
			tArgs.processor(processor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory()); //协议
			TServer server = new TThreadPoolServer(tArgs);

			System.out.println("Starting the TThreadPoolServer ...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	/**
	 * 非阻塞io 单线程处理所有的 accept/read/write 和业务逻辑
	 * 必须使用 TFramedTransport
	 * @throws Exception
	 */
	@Test
	public void testTNonblockingServer() throws Exception{

		try {
			TProcessor processor = getProcessor();
			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(PORT); //transport

			TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverTransport);
			tArgs.processor(processor); //processor

			//必须使用TFramedTransport
			tArgs.transportFactory(new TFramedTransport.Factory());

			tArgs.protocolFactory(new TBinaryProtocol.Factory()); //协议
			TServer server = new TNonblockingServer(tArgs);

			System.out.println("Starting the TNonblockingServer ...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * THsHaServer在TNonblockingServer的基础上改进使用线程池来处理业务逻辑
	 * 非阻塞io 线程池
	 * accept/read/write在selector线程，业务逻辑在线程池。
	 * 必须使用 TFramedTransport
	 * @throws Exception
	 */
	@Test
	public void testTHsHaServer() throws Exception{

		try {
			TProcessor processor = getProcessor();
			TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(PORT); //transport

			THsHaServer.Args tArgs = new THsHaServer.Args(serverTransport);
			tArgs.processor(processor); //processor

			//必须使用TFramedTransport
			tArgs.transportFactory(new TFramedTransport.Factory());

			tArgs.protocolFactory(new TBinaryProtocol.Factory()); //协议
			TServer server = new THsHaServer(tArgs);

			System.out.println("Starting the THsHaServer ...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
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



}
