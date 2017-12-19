package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeArrays {

	public static void main(String[] args) {
		Integer a[] = {1,3,5,7,9};
		Integer b[] = {2,4,6,7,8};
		List<Integer> ac = new ArrayList<Integer>(Arrays.asList(a));
		List<Integer> bc = new ArrayList<Integer>(Arrays.asList(b));
		List<Integer> cc = new ArrayList<Integer>();
		
		for(int i=0; i<a.length; i++) {
			if(!cc.contains(a[i])) {
				cc.add(a[i]);
			}
		}
		
		for(int i=0; i<b.length; i++) {
			if(!cc.contains(b[i])) {
				cc.add(b[i]);
			}
		}
		
		for(int i=0; i<cc.size(); i++) {
			//System.out.println(cc.get(i));
		}
		
		Integer[] c = cc.parallelStream().toArray(Integer[]::new);
		
		for(int i=0; i<c.length; i++) {
			System.out.println(c[i]);
		}
	}

}
