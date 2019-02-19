package rpc;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;

public class Test {
	public static void main(String []args) {
		
			Stub original = new Stub();
	    	int port = 4444;
	        JSONObject jsonObj = new JSONObject();
	    	RPCSender handler = new RPCSender(original, port);
	    	intf f = (intf) Proxy.newProxyInstance(intf.class.getClassLoader(),
	                new Class[] { intf.class },
	                handler);
	    	
	    	System.out.println("Result:" + f.add(2, 3, 4));
	    	System.out.println("Result: " + f.mul(4 , 5));
	    	System.out.println("Result:" + f.add(10, 30, 40));

	    	//System.out.println("Output:" + f.getFullName("Bhavin", "Kotak") );
    	   /*List<Callable<Void>> taskList = new ArrayList<Callable<Void>>();
    	   

    	   //create a pool executor with 3 threads
    	   ExecutorService executor = Executors.newFixedThreadPool(5);

    	  
    	for(int i = 0; i < 1; i++) {
    		taskList.add(new Callable<Void>()
     	   {
      	      @Override
      	      public Void call() throws Exception
      	      {
      	    	Stub original = new Stub();
      	    	int port = 4444;
      	        JSONObject jsonObj = new JSONObject();
      	    	RPCSender handler = new RPCSender(original, port);
      	    	IMethods f = (IMethods) Proxy.newProxyInstance(IMethods.class.getClassLoader(),
      	                new Class[] { IMethods.class },
      	                handler);
      	    	  System.out.println("Output:" + f.getFullName("Bhavin", "Kotak") );
      	    	  return null;
      	      }
      	   }
      	   );
     	   	taskList.add(new Callable<Void>()
     	   {
      	      @Override
      	      public Void call() throws Exception
      	      {
      	    	Stub original = new Stub();
      	    	int port = 4444;
      	        JSONObject jsonObj = new JSONObject();
      	    	RPCSender handler = new RPCSender(original, port);
      	    	IMethods f = (IMethods) Proxy.newProxyInstance(IMethods.class.getClassLoader(),
      	                new Class[] { IMethods.class },
      	                handler);
      	    	  int result = f.add(10, 2);
      	            
      	           System.out.println("Output:" + result);
      	         return null;
      	      }
      	   });           
            
            
    	}
    	try
	  	   {
	  	      //start the threads and wait for them to finish
	  	      executor.invokeAll(taskList);
	  	   }
  	   	catch (InterruptedException ie)
	  	   {
	  	      //do something if you care about interruption;
  	   		System.out.println("Exception");
  	   		
	  	   }
	  	   */
    	
	}
}
