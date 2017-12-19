package com.test;

public class FindPair {

	public static void main(String[] args) {
		int a[] = {1,2,3,4,5};
		int x = 6;
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a.length; j++) {
				if(a[i]+a[j] == x) {
					System.out.println(a[i] + " + " + a[j] + " = " + x);
				}
			}
		}
	}

}
