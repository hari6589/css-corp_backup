package com.test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) {
		test1();
	}
	
	public static void test0() {
		String testPojoStr = "{\"name\":\"Test\",\"bDate\":null}";
		ObjectMapper mapper = new ObjectMapper();
		TestPojo testPojo = null;
		try {
			testPojo = mapper.readValue(testPojoStr, TestPojo.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(testPojo.getName());
	}
	public static void test() {
		String dtStr = "20171027 10:00";
		//String dtStr = "1509195600000";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
		String dateStr = dateFormat.format(new Date());
		System.out.println("Date Str : " + dateStr);
		try {
			dateFormat.parse(dateStr);
		} catch (ParseException e) {
			System.out.println("ParseException : " + e);
		}
		String[] dateSplits = dateStr.split("\\s+");
		String selectedDate = dateSplits[0];
		String[] hourMinSplits = dateSplits[1].split(":");
		Integer selectedTime = new Integer(hourMinSplits[0]) * 60 + new Integer(hourMinSplits[1]);
		System.out.println(selectedDate);
		System.out.println(selectedTime);
	}
	
	public static void test1() {
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");
			Date date = df.parse("20171114 14:00");
		} catch(ParseException e) {
			System.out.println("ParseException");
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String dateStr = "20171114 14:00";
		String[] dateSplits = dateStr.split("\\s+");
		System.out.println(dateSplits.length);
		String selectedDate = dateSplits[0];
		String[] hourMinSplits = dateSplits[1].split(":");
		Integer selectedTime = new Integer(hourMinSplits[0]) * 60 + new Integer(hourMinSplits[1]);
		System.out.println(selectedDate);
		System.out.println(selectedTime);
	}
}
