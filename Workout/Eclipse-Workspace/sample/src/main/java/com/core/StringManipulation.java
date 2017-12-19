package com.core;

public class StringManipulation {

	public static void main(String[] args) {
		String a = "Hello";
		String b = "Hello1";
		System.out.println(a);
		System.out.println(b);
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		
		//
		System.out.println(a==b);
		System.out.println(a.equals(b));
		
		//
		StringBuffer strBuf = new StringBuffer("Hello");
		System.out.println(strBuf);
		System.out.println(strBuf.hashCode());
		strBuf.append(" World!");
		System.out.println(strBuf.hashCode());
		
		//
		String str1 = new String("test");
		String str2 = new String("test");
		
		System.out.println(str1.hashCode());
		System.out.println(str2.hashCode());
		
		//
		StringManipulation sm = new StringManipulation();
		System.out.println(sm.hashCode());
		
		//
		String myString = new String( "old String" );
	    System.out.println( myString );
	    myString.replaceAll( "old", "new" );
	    System.out.println( myString );
	    myString = "jhgg";
	    System.out.println( myString );
		
	    System.out.println("___________________");
	    String one = "abc";
	    String two = "ab"+"c";
	    String three = "abc";
	    String four = new String("abc");
	    System.out.println(one.intern() + " " + two.intern() + " " + three.intern() + " " + four.intern());
	    System.out.println(one.hashCode() + " " + two.hashCode() + " " + three.hashCode() + " " + four.hashCode());
	    System.out.println(one==two);
	    System.out.println(two==three);
	    System.out.println(three==four);
	       
	}

}
