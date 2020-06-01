package andy.com.internet.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

public class MultiplicationClient {

	final static String Host = "localhost";
	final static int Port = 19090;

	/**
	 * 可以用于测试阻塞Server( TSimpleServer 和TThreadPoolServer)
	 */
	@Test
	public  void testBioClient(){

		try {
			  TTransport transport = new TSocket(Host,Port);
		      transport.open();
		      TProtocol protocol = new TBinaryProtocol(transport);	 //协议
			  perform(protocol);
			  transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
		 catch (Exception x) {
				x.printStackTrace();
			}
	}


	/**
	 * 可以用于测试非阻塞Server( TNoblockingServer ,THaHsServer,TThreadedSelectorServer)
	 */
	@Test
	public  void testNioClient(){

		try {
			TTransport transport = new TSocket(Host,Port);
			TFramedTransport transport1 = new TFramedTransport(transport);
			transport1.open();

			TProtocol protocol = new TBinaryProtocol(transport1);	 //协议
			perform(protocol);
			transport.close();
		} catch (TException x) {
			x.printStackTrace();
		}
		catch (Exception x) {
			x.printStackTrace();
		}
	}



	private static void perform(TProtocol protocol)throws TException {

		MultiplicationService.Client client = new MultiplicationService.Client(	protocol);
		int product = client.multiply(3, 5);
		System.out.println("3*5=" + product);
	}
}