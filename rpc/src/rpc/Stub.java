package rpc;
public class Stub extends RPCSender implements rpc.intf{

	public int add(int v1,int v2,int v3) {
		return (int)result;
 	}
	public double mul(double v1,int v2) {
		return (double)result;
 	}
 }