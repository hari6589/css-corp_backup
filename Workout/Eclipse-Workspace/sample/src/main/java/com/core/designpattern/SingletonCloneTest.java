package com.core.designpattern;

public class SingletonCloneTest implements Cloneable {

	private static SingletonCloneTest singletonTest;
	
	private SingletonCloneTest() { }
	
	static {
		singletonTest = new SingletonCloneTest();
	}
	
	private static SingletonCloneTest getInstance() {
		return singletonTest;
	}
	
	public void test() {
		System.out.println("Working!");
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public static void main(String[] args) {
		SingletonCloneTest singletonTest = getInstance();
		System.out.println(singletonTest.hashCode());
		singletonTest.test();
		
		try {
			SingletonCloneTest newS = (SingletonCloneTest) singletonTest.clone();
			System.out.println(newS.hashCode());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
