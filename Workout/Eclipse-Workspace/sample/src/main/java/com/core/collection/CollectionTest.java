package com.core.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class CollectionTest {

	public static void main(String[] args) {
		CollectionTest test = new CollectionTest();
		//test.testArrayList();
		//test.testLinkedList();
		//test.setTest();
		test.setTest1();
	}

	/*
	 * - ArrayList internally uses dynamic array to store the elements
	 * - Manipulation with ArrayList is slow because it internally uses array. If any element is removed from the array, all the bits are shifted in memory.
	 * - ArrayList class can act as a list only because it implements List only.
	 * - ArrayList is better for storing and accessing data.
	 */
	public void testArrayList() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("aa");
		arrayList.add("bb");
		
		for(int i=0; i < arrayList.size(); i ++) {
			System.out.println(arrayList.get(i));
		}
		System.out.println("---");
		Iterator<String> it = arrayList.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println("---");
		for(String st : arrayList) {
			System.out.println(st);
		}
	}
	
	/*
	 * - LinkedList internally uses doubly linked list to store the elements.
	 * - Manipulation with LinkedList is faster than ArrayList because it uses doubly linked list so no bit shifting is required in memory.
	 * - LinkedList class can act as a list and queue both because it implements List and Deque interfaces.
	 * - LinkedList is better for manipulating data.
	 */
	public void testLinkedList() {
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		linkedList.add(11);
		linkedList.add(22);
		
		Iterator<Integer> itr = linkedList.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		System.out.println("---");
		ListIterator<Integer> litr = linkedList.listIterator();  
		System.out.println("traversing elements in forward direction...");  
		while(litr.hasNext()){  
			System.out.println(litr.next());  
		}
		System.out.println("-");
		System.out.println("traversing elements in backward direction...");  
		while(litr.hasPrevious()){  
			System.out.println(litr.previous());  
		}
	}
	
	public void setTest() {
		Set<String> set = new HashSet<String>();
		System.out.println(set.size());
		set.add("");
		System.out.println(set.size());
		set.add("");
		System.out.println(set.size());
		set.add("");
		System.out.println(set.size());
		set.add("");
		System.out.println(set.size());
	}
	
	public void setTest1() {
		Map<String, String> productCodeMap = new HashMap<String, String> ();
		productCodeMap.put("", "Empty");
		productCodeMap.put("", "Empty1");
		productCodeMap.put("", "Empty2");
		System.out.println(productCodeMap.toString());
	}
	
	public void vectorTest() {
		
	}
}
