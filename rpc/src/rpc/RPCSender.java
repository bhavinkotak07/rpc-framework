package rpc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RPCSender implements InvocationHandler{
	private final intf original;
	Object result;
	private ServerSocket server;
	Socket socket = null; 
	private int port;
	public static int requestId = 0;
	public RPCSender(){
		original = null;
	}
    public RPCSender(intf original, int port) {
        this.original = original;
        this.port = port;
        this.result = new Object();
    }
    public static Object toObject( Class clazz, String value ) {
	    if( Boolean.class == clazz ) return Boolean.parseBoolean( value );
	    if( Byte.class == clazz ) return Byte.parseByte( value );
	    if( Short.class == clazz ) return Short.parseShort( value );
	    if( Integer.class == clazz ) return Integer.parseInt( value );
	    if( Long.class == clazz ) return Long.parseLong( value );
	    if( Float.class == clazz ) return Float.parseFloat( value );
	    if( Double.class == clazz ) return Double.parseDouble( value );
	    if( int.class == clazz ) return Integer.parseInt( value );
	    if( double.class == clazz ) return Double.parseDouble( value );
	    if( boolean.class == clazz ) return Boolean.parseBoolean( value );
	    if( long.class == clazz ) return Long.parseLong( value );
	    if( float.class == clazz ) return Float.parseFloat( value );

	    return value;
	}
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Exception {
        System.out.println("BEFORE:" + requestId);
        //result = method.invoke(original, args);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("MethodName", method.getName());
        jsonObj.put("RequestId", requestId++);
        jsonObj.put("NumberOfParameters", method.getParameterCount());
        JSONArray params = new JSONArray();
        for(int i = 0; i < method.getParameterTypes().length; i++) {
        	JSONObject paramObj = new JSONObject();
        	paramObj.put("Type",method.getParameterTypes()[i].getName() );
        	paramObj.put("Value", args[i]);
        	params.add(paramObj);
        }
        jsonObj.put("Parameters", params);
        jsonObj.put("ReturnType", method.getReturnType().getName());
        System.out.println(jsonObj.toJSONString());

        
        socket = new Socket("localhost", this.port);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(jsonObj.toJSONString());
        
        DataInputStream in = new DataInputStream(socket.getInputStream());
        String responseString =  (String)in.readUTF();
		JSONObject ack = (JSONObject)new JSONParser().parse(responseString );
		
		
        System.out.println("AFTER");
        System.out.println(ack.toJSONString());
        Class type = method.getReturnType();
        if( ack.get("Status").toString().equals("Success") )
        	result = toObject( type ,ack.get("Result").toString() );
        else
        	throw new Exception(ack.get("Message").toString() );
        /*if(type.isInstance(String.class))
        	result = (String) result;
        if(type.isInstance(Integer.class))
        	result = (Integer) result;
        if(type.isInstance(int.class))
        	result = ( int) result;
        if(type.isInstance(Double.class))
        	result = (Double) result;
        if(type.isInstance(double.class))
        	result = (Double) result;
        	*/
        return  result;
    }
	
	
	
	public void send() {
		
	}

}
