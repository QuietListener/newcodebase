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

	@Test
	public  void test1(){

		try {
			  TTransport transport = new TSocket(Host,Port);
		      transport = new TFramedTransport(transport);
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



	private static void perform(TProtocol protocol)throws TException {

		MultiplicationService.Client client = new MultiplicationService.Client(	protocol);
		int product = client.multiply(3, 5);
		System.out.println("3*5=" + product);
	}
}