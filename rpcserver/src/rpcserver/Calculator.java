package rpcserver;

public class Calculator implements IMethods {

	@Override
	public int add(int a, int b) {
		// TODO Auto-generated method stub
		System.out.println("Inside add");
		return a + b;
	}

	@Override
	public int mul(int a, int b) {
		// TODO Auto-generated method stub
		System.out.println("Inside mul");

		return a * b;
	}

	@Override
	public void sub(int a, int b, int c) {
		// TODO Auto-generated method stub
		System.out.println("Inside sub");

	}

	@Override
	public void div() {
		// TODO Auto-generated method stub
		System.out.println("Inside div");

	}
	@Override
	public String getFullName(String firstName, String lastName) {
		return firstName + lastName;
	}
	
}