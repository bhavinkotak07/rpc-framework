package rpc;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
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
	RPCListener(){
		
	}
	RPCListener(int port) throws IOException{
		server = new ServerSocket(port);
		
	}
	void startListener() throws IOException, ParseException {
		while(true) {
			socket = server.accept();
			DataInputStream in = new DataInputStream( new BufferedInputStream(socket.getInputStream())); 
			String inputString = in.readUTF();
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(inputString);
			
		}
		
		
	}
	
	

}
