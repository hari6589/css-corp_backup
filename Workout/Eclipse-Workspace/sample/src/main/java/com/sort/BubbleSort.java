package com.sort;

public class BubbleSort {

	public static void main(String[] args) {
		int[] arr = {3, 4, 2, 5, 1};
		int length = arr.length;
		for(int i=0; i<length; i++) {
			for(int j=i+1; j<length; j++) {
				if(arr[i] > arr[j]) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		for(int i=0; i<length; i++) {
			System.out.println(arr[i]);
		}
	}
}
