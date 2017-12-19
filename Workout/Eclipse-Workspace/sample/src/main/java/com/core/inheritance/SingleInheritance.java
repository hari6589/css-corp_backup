package com.core.inheritance;

class ClassA {
	
	ClassA() {
		System.out.println("ClassA constructor");
	}
	
	public void dispA() {
		System.out.println("ClassA dispA method!");
	}
}

public class SingleInheritance extends ClassA {
	
	public SingleInheritance() {
		System.out.println("SingleInheritance constructor");
	}
	
	public void dispB() {
		System.out.println("ClassB dispB method!");
	}
	
	public static void main(String []args) {
		SingleInheritance b = new SingleInheritance();
		b.dispB();
		b.dispA();
	}
}