package rpcserver;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConcurrentRequestHandler implements Runnable {
	static int counter = 0;
	
	public Socket socket;
	//public IMethods imethods;
	public String interfaceName;
	public String className;
	public ConcurrentRequestHandler(Socket socket, String intrfaceName, String className) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.interfaceName = interfaceName;
		this.className = className;
		counter += 1;
	}

	public static Class<?> parseType(final String className) {
	    switch (className) {
	        case "boolean":
	            return boolean.class;
	        case "byte":
	            return byte.class;
	        case "short":
	            return short.class;
	        case "int":
	            return int.class;
	        case "long":
	            return long.class;
	        case "float":
	            return float.class;
	        case "double":
	            return double.class;
	        case "char":
	            return char.class;
	        case "void":
	            return void.class;
	        default:
	            String fqn = className.contains(".") ? className : "java.lang.".concat(className);
	            try {
	                return Class.forName(fqn);
	            } catch (ClassNotFoundException ex) {
	                throw new IllegalArgumentException("Class not found: " + fqn);
	            }
	    }
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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		JSONObject obj = null;
		try {
			DataInputStream in = new DataInputStream( new BufferedInputStream(socket.getInputStream())); 
			String inputString = in.readUTF();
			System.out.println("Listener: " + inputString);
			JSONParser parser = new JSONParser();
			obj = (JSONObject)parser.parse(inputString);
			Class cls = Class.forName(className);
			Object concreteClass = cls.newInstance();
			Object result = new Object();
			boolean flag = false;
			for(Method method : cls.getDeclaredMethods() ) {
				int size = Integer.parseInt( obj.get("NumberOfParameters").toString() );
				Object []args = new Object[ size ];
				Object [] parameters = new Object[ size ];
				JSONArray arrayOfParameters = (JSONArray) obj.get("Parameters");
				for(int i = 0; i < size; i++ ) {
					JSONObject objParm =  (JSONObject)arrayOfParameters.get(i);
					Class argType = parseType( objParm.get("Type").toString() );
					//System.out.println(objParm.get("Type").toString() + ":" + objParm.get("Value"));
					parameters[i] = toObject(argType, objParm.get("Value").toString() ) ;
				}
				if( obj.get("MethodName").toString().equals(method.getName() ) ){
					System.out.println("Calling method : " + method.getName());
					result = method.invoke(cls.cast(concreteClass), parameters);
					
				}
			}
			
			DataOutputStream out    = new DataOutputStream(socket.getOutputStream());
			JSONObject responseObj = new JSONObject();
			responseObj.put("Status", "Success");
			responseObj.put("Result", result);
			responseObj.put("RequestId", obj.get("RequestId"));

			out.writeUTF(responseObj.toJSONString() );
			System.out.println(result);
			System.out.println("Request Id:" + obj.get("RequestId"));
			out.close();
			in.close();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.getStackTrace());
			DataOutputStream out = null;
			try {
				out = new DataOutputStream(socket.getOutputStream());
				JSONObject responseObj = new JSONObject();
				responseObj.put("Status", "Error");
				responseObj.put("Message", ex.getMessage());
				responseObj.put("RequestId", obj.get("RequestId"));

				out.writeUTF(responseObj.toJSONString() );
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}
