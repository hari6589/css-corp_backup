package samples;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeManipulation {

	public static void main(String[] args) {
		
		DateTimeManipulation dt = new DateTimeManipulation();
		
		//dt.extractDateAndTime();
		
		//dt.strMilliSecondsToDate();
		
		//dt.createUniqueueIdByTimestamp();
		
		//dt.loopUntilTime();
		
		//dt.breakWhenTimeUp();
		
		dt.getYesterdayTimeslot();
	}
	
	public void strMilliSecondsToDate() {
		String dateStr = "1455811200000";
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");

		long milliSeconds= Long.parseLong(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		System.out.println(formatter.format(calendar.getTime()));
	}
	
	public void extractDateAndTime() {
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm");
		String dateStr = dateFormat.format(today);
	
		Date choiceDateTime = null;
		try {
			choiceDateTime = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			System.out.println("Date format parsing exception!");
		}
		
		System.out.println("Choice Date Time: " + choiceDateTime);
		
		String[] dateSplits = dateStr.split("\\s+");
		String selectedDate = dateSplits[0];
		String[] hourMinSplits = dateSplits[1].split(":");
		Integer selectedTime = new Integer(hourMinSplits[0]) * 60 + new Integer(hourMinSplits[1]);
		
		System.out.println("Selected Date : " + selectedDate);
		System.out.println("Selected Time : " + selectedTime);
	}

	public void createUniqueueIdByTimestamp() {
		Date today = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestampStr = dateFormat.format(today);
		System.out.println("Selected Date : " + timestampStr);
	}

	public void loopUntilTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS");
		System.out.println("Start Date : " + dateFormat.format(new Date()));
		
		int min = 4;
		int sec = 30;
		
		final long NANOSEC_PER_SEC = 1000l*1000*1000;
		long startTime = System.nanoTime();
		
		
		while ((System.nanoTime()-startTime) < min * sec * NANOSEC_PER_SEC) {
		  // do stuff
		}
		System.out.println("End Date : " + dateFormat.format(new Date()));
	}
	
	public void breakWhenTimeUp() {
		int min = 6;
		int sec = 60;
		
		final long NANOSEC_PER_SEC = 1000l*1000*1000;
		long startTime = System.nanoTime();
		
		Calendar cal = Calendar.getInstance();
		Date start = cal.getTime();
		cal.add(Calendar.MINUTE, 4);
		cal.add(Calendar.SECOND, 30);
		
		Date deadLine = cal.getTime();
		System.out.println("Start1 : " + start);
		System.out.println("Start2 : " + new Date());
		System.out.println("Deadline : " + deadLine);
		
		while ((System.nanoTime()-startTime) < min * sec * NANOSEC_PER_SEC) {
		  if(new Date().after(deadLine)) {
			  break;
		  }
		}
		System.out.println("End : " + new Date());
	}
	
	public void getYesterdayTimeslot() {
		DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy"); // HH:mm:ss
		Calendar cal = Calendar.getInstance();
		System.out.println("Current Time : " + dateTimeFormat.format(cal.getTime()));
		cal.add(Calendar.DATE, -2);
		System.out.println("Start Time : " + dateTimeFormat.format(cal.getTime()) + " 23:59:59");
		cal.add(Calendar.DATE, +1);
		System.out.println("Start Time : " + dateTimeFormat.format(cal.getTime()) + " 00:00:00");
	}
}
