package com.core.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableTest {

	public static void main(String[] args) {
		List<Pojo> pojoList = new ArrayList<Pojo>();
		pojoList.add(new Pojo(1, "A", 22));
		pojoList.add(new Pojo(2, "B", 26));
		pojoList.add(new Pojo(3, "C", 19));
		
		System.out.println("Before Sorting:");
		for(Pojo p : pojoList) {
			System.out.println(p.getId() + " _ " + p.getName() + " _ " + p.getAge());
		}
		Collections.sort(pojoList);
		
		System.out.println("After Sorting:");
		for(Pojo p : pojoList) {
			System.out.println(p.getId() + " _ " + p.getName() + " _ " + p.getAge());
		}
	}
}

class Pojo implements Comparable<Pojo> {
	
	private int id;
	private String name;
	private int age;
	
	public Pojo(int id, String name, int age) {
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
	public int compareTo(Pojo o) {
		if(o.age == this.age)
			return 0;
		else if(o.age < this.age)
			return 1;
		else
			return -1;
	}
	
}