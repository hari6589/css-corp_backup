package com.core.marker;

public class TestMarker implements TestInterface {

	public static void main(String[] args) {
		TestMarker tm = new TestMarker();
		System.out.println(tm instanceof TestInterface);
	}

}
