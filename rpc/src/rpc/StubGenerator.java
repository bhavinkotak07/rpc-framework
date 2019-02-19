package rpc;

import java.io.File;
import java.io.FileWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

public class StubGenerator {
	
	public static void main(String []args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
        try {
        	HashMap <String, Class> map = new HashMap<String, Class>();
        	map.put("String", String.class);
        	map.put("int", int.class);
        	
        	HashMap <String, String> returnMapper = new HashMap<String, String>();
        	returnMapper.put("String", "String");
        	returnMapper.put("int", "Integer");
        	
        	File file = new File("src/rpc/Stub.java");
        	  
            //Create the file
            if (file.createNewFile()){
              System.out.println("File is created!");
            }else{
              System.out.println("File already exists.");
            }
             
            //Write Content
            FileWriter writer = new FileWriter(file);
            
        	Class test = Class.forName("rpc.intf");
        	writer.write("package rpc;\n");
        	writer.write("public class Stub extends RPCSender implements " + test.getName() + "{\n");
        	for ( Method method : test.getDeclaredMethods() ) {
        		String op = "";
        		op += "\n\tpublic " + method.getReturnType().getName() + " " + method.getName() + "(";
        		for(Annotation tv : method.getAnnotations() ) {
        			System.out.println(tv.toString());
        		}
        		int count = 1;
        		for( Type t : method.getParameterTypes() ) {
        			op +=  t.getTypeName() + " v" + count++ + ",";
        		}
        		if(method.getParameterTypes().length > 0)
        			op = op.substring(0, op.length() - 1);
        		op += ") {\n\t";
        		if(!method.getReturnType().getName().equals("void") ) {
        			op += "\treturn (" + method.getReturnType().getName() + ")result;";
        		}
        		op += "\n \t}";
        		System.out.println(op);
        		writer.write(op);
                

        		
        	}
        	writer.write("\n }");
        	writer.close();
        	
        	
        	
            
            
        	/*Class cls = Class.forName("rpc.Test"); 
        	int size = 2;
        	Class params[] = new Class[size]; //{ String.class, int.class } ;
        	
        	
        	Object []objparams = new Object[size];
        	
        	String []parameters = {  "Hello world", "12" }; 
        	String []types = { "String", "int" };
        	for(int i = 0; i < parameters.length; i++) {
        		if ( types[i].equals("int") ) {
        			objparams[i] = ( Integer.parseInt( parameters[i] ) );
        			params[i] = map.get(types[i]);
        		}
        			
        		else {
        			objparams[i] =(parameters[i] );
        			params[i] = map.get(types[i]);
        		}
        			
        	}
        	
        	
        	Method methodcall1 = cls.getDeclaredMethod("display", params);
        	Class returnType = methodcall1.getReturnType();
			Object result = methodcall1.invoke(cls.newInstance(), objparams); 
			
			System.out.println("Result:" +  result);
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
