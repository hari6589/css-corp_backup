package com.test;

public class NumberPalindrom {

	public static void main(String[] args) {
		int a = 12321;
		int b = a;
		int c = 0;
		while(b > 0) {
			int temp = b % 10;
			c = c * 10 + temp;
			b = b/10;
		}
		if(a == c) {
			System.out.println("The given number " + a + " is Palindrom!");
		} else {
			System.err.println("The given number " + a + " is not Palindrom!");
		}
	}

}
