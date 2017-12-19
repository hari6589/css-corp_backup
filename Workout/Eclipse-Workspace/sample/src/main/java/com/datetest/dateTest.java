package com.datetest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateTest {

	public static void main(String[] args) throws ParseException {
		String str = "20171122 18:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		Date date = sdf.parse(str);
		System.out.println(str);
		System.out.println(date);
	}

}
