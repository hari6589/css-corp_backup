package com.test;

import java.util.Calendar;
import java.util.TimeZone;

public class TestCal {

	public static void main(String[] args) {
		//Calendar c = Calendar.getInstance();
		Calendar c = Calendar.getInstance();
		//c.setTimeZone(TimeZone.getTimeZone("Etc/GMT+6"));
		c.setTimeInMillis(1512165600000L);
		System.out.println(c.get(Calendar.YEAR));
		System.out.println(c.get(Calendar.MONTH));
		System.out.println(c.get(Calendar.DATE));
		System.out.println("---");
		System.out.println(c.get(Calendar.HOUR));
		System.out.println(c.get(Calendar.HOUR_OF_DAY));
		System.out.println(c.get(Calendar.MINUTE));
		
		// get Date
		System.out.println(c.getTime());
		
		System.out.println("____");
		
		String s = "2749";
		
		if (s.endsWith(",")) {
			s = s.substring(0, s.length() - 1);
		}
		System.out.println(s);
		String sp[] = s.split("_");
		System.out.println(sp.length);
	}

}
