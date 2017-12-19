package com.core.designpattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonTestReflection {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SingletonTest1 st1 = SingletonTest1.singletonTest;
		SingletonTest1 st2 = null;
		
		Constructor[] constructors = SingletonTest1.class.getDeclaredConstructors();
		for(Constructor constructor : constructors) {
			constructor.setAccessible(true);
			st2 = (SingletonTest1) constructor.newInstance();
			break;
		}

		System.out.println(st1.hashCode());
		System.out.println(st2.hashCode());
	}

}

/*
class SingletonTest1 {
	
	public static SingletonTest1 singletonTest = new SingletonTest1();
	
	private SingletonTest1() {}
	
}
*/