package com.core.designpattern;

import java.util.ArrayList;
import java.util.List;

public class FindDuplicateOneToN {

	public static void main(String[] args) {
		List<Integer> numbers = new ArrayList<Integer>();
		for(int i=1; i <= 30; i++) {
			numbers.add(i);
		}
		numbers.add(22);
		FindDuplicateOneToN obj = new FindDuplicateOneToN(); 
		System.out.println("Duplicate Value : " + obj.findDuplicate(numbers));
	}

	public int sumAll(List<Integer> numbers) {
		int total = 0;
		System.out.println("Size : " + numbers.size());
		for(int number:numbers) {
			total += number;
		}
		System.out.println("Total : " + total);
		return total;
	}
	
	public int findDuplicate(List<Integer> numbers) {
		int total = sumAll(numbers);
		int highestNumber = numbers.size() - 1;
		System.out.println("Sum of Numbers 1 to n : " + highestNumber * (highestNumber + 1) / 2);
		int duplicate = total - (highestNumber * (highestNumber + 1) / 2);
		System.out.println("Highest: " + highestNumber);
		return duplicate;
	}
	
}
