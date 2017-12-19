package com.core.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {

	public static void main(String[] args) {
		List<Pojo1> pojoList = new ArrayList<Pojo1>();
		pojoList.add(new Pojo1(1, "A", 22));
		pojoList.add(new Pojo1(2, "B", 26));
		pojoList.add(new Pojo1(3, "C", 19));
		
		System.out.println("Before Sorting:");
		for(Pojo1 p : pojoList) {
			System.out.println(p.getId() + " _ " + p.getName() + " _ " + p.getAge());
		}
		
		Collections.sort(pojoList, new ageComparator());
		System.out.println("After Age Sorting:");
		for(Pojo1 p : pojoList) {
			System.out.println(p.getId() + " _ " + p.getName() + " _ " + p.getAge());
		}
		
		Collections.sort(pojoList, new nameComparator());
		System.out.println("After Name Sorting:");
		for(Pojo1 p : pojoList) {
			System.out.println(p.getId() + " _ " + p.getName() + " _ " + p.getAge());
		}
	}

}

class ageComparator implements Comparator<Pojo1> {
	@Override
	public int compare(Pojo1 o1, Pojo1 o2) {
		Pojo1 p1 = (Pojo1) o1;
		Pojo1 p2 = (Pojo1) o2;
		
		if(p1.getAge() == p2.getAge())
			return 0;
		else if(p1.getAge() > p2.getAge())
			return 1;
		else
			return -1;
	}
}

class nameComparator implements Comparator<Pojo1> {
	@Override
	public int compare(Pojo1 o1, Pojo1 o2) {
		return o1.getName().compareTo(o2.getName());
	}
}

class Pojo1 implements Comparable<Pojo1> {
	
	private int id;
	private String name;
	private int age;
	
	public Pojo1(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public int compareTo(Pojo1 o) {
		if(o.age == this.age)
			return 0;
		else if(o.age < this.age)
			return 1;
		else
			return -1;
	}
	
}