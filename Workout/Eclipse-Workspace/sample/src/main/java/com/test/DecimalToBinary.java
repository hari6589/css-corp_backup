package com.test;

public class DecimalToBinary {

	public static void main(String[] args) {
		int d = 26;
		String b = "";
		System.out.println(d);
		while(d>0) {
			int a = d%2;
			b = a + b;
			d = d/2;
		}
		System.out.println(b);
	}

}
