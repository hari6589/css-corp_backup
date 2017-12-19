package com.core.collection.concurrent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentTest {
	public static void main(String[] args) {
		//Map<String, String> map = new HashMap<String, String>();
		
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		
		map.put("1", "One");
		map.put("2", "Two");
		map.put("3", "Three");
		
		System.out.println("Map Size : " + map.size());
		
		Iterator<String> itr = map.keySet().iterator();
		while(itr.hasNext()) {
			String key = itr.next();
			System.out.print("Key : " + key + " : ");
			System.out.println(map.get(key));
			if(key.equals("1")){
				map.remove("3");
				map.put("4", "4");
				map.put("5", "5");
			}
		}
		System.out.println("Map Size : " + map.size());
	}
}
