package andy.com.internet.thrift;

import org.apache.thrift.TException;

public class MultiplicationHandler implements MultiplicationService.Iface{

	public int multiply(int n1, int n2) throws TException {
		
		System.out.println("receive "+n1+"+"+n2);
		return n1 * n2;
	}

}
