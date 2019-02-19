package rpcserver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class RPCListener {
	private ServerSocket server;
	Socket socket = null; 
	private int port;
	Object result;
	//public IMethods imethods;
	public String interfaceName;
	public String className;
	public RPCListener(){
		
	}
	public RPCListener(String interfaceName, String className, int port) throws IOException{
		server = new ServerSocket(port);
		this.interfaceName = interfaceName;
		this.className = className;
		this.port = port;
	}

	void startListener() throws IOException, ParseException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Listener accepting connections on port: " + port);
		while(true) {
			socket = server.accept();

			Thread t = new Thread(new ConcurrentRequestHandler(socket, this.interfaceName, this.className));
			t.run();
			System.out.println("Counter:" + ConcurrentRequestHandler.counter);
			
		}
		
		
	}
	
	

}
