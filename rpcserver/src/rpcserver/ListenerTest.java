package rpcserver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.json.simple.parser.ParseException;

public class ListenerTest {
	public static void main(String [] args) throws IOException, ClassNotFoundException {
		Calculator calc = new Calculator();
		String interfaceName = "rpcserver.intf";
		String className = "rpcserver.servclass";
		Class cls = Class.forName(className);

		RPCListener listener = new RPCListener(interfaceName, className, 4444);
		try {
			listener.startListener();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}
