package samples;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		//System.out.println(new SimpleDateFormat("MM-dd-yyyy h:mm a").format("04-MAY-17"));
		//System.out.println(new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011"));
		//System.out.println(new SimpleDateFormat("MM-dd-yyyy h:mm a").format(new SimpleDateFormat("dd-MMM-yy").parse("04-MAY-17")));
		
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateFormat.format(new Date()));
//		
//		System.out.println(new SimpleDateFormat("dd-MMM-yy").format(new Date()));
//		//"01-MAY-17"
		
//		TimeZone zone2 = TimeZone.getTimeZone("America/New_York");
//		DateFormat format2 = DateFormat.getDateTimeInstance();
//		format2.setTimeZone(zone2);
//
//		System.out.println("C : " + format2.format(new Date()));
		
		test();
		//shiftTimeZone();
		//tq();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date d = getRptTime(sdf.format(date));
	}

	public static void test() {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt = "2007-06-27 00:00:00";
		try {
			System.out.println("C : " + dtf.format(dtf.parse(dt)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TimeZone zone1 = TimeZone.getTimeZone("America/New_York");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		sdf.setTimeZone(zone1);
		try {
			System.out.println("C : " + sdf.format(dtf.parse(dt)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TimeZone zone2 = TimeZone.getTimeZone("UTC");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		sdf2.setTimeZone(zone2);
		try {
			System.out.println("C : " + sdf2.format(dtf.parse(dt)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void test1() throws ParseException {
		System.out.println(new Date());
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
		System.out.println(new Date());
		
		String startDateString = "2007-06-27 12:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("EST"));
		Date yourUtcDate = sdf.parse(startDateString);
		System.out.println("EST : " + sdf.format(yourUtcDate));
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		System.out.println("UTC : " + sdf.format(yourUtcDate));
		
	}
	
	private static void shiftTimeZone() throws ParseException {
		String tzid = "America/New_York";
		TimeZone tz = TimeZone.getTimeZone(tzid);
		
		String startDateString = "2007-06-27 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = sdf.parse(startDateString);
		
		// timezone symbol (z) included in the format pattern for debug
		DateFormat format = new SimpleDateFormat("yy/M/dd hh:mm a z");
		
		// format date in default timezone
		System.err.println(format.format(d));
		
		// format date in target timezone
		format.setTimeZone(tz);
		System.err.println(format.format(d));
	}
	
	private static void tq() {
		System.out.println(getCurrentTime("Etc/GMT+6"));
	}
	
	public static String getCurrentTime(String zoneId) {

		if (zoneId == null) {
			zoneId = "Etc/GMT+6";
		}

		ZoneId defaultZoneId = ZoneId.of(zoneId);
		DateTimeFormatter createdDateFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(LocalDateTime.now());
		ZonedDateTime systemDefaultZone = ldt.atZone(defaultZoneId);
		String createdDate = systemDefaultZone.format(createdDateFormat);
		return createdDate;

	}
	
	public static String commonDateFormatStr = "yyyy-MM-dd HH:mm:ss";
	public static String reportDateFormatStr = "MM/dd/yyyy HH:mm:ss";
	public static String utcTimeZoneId = "UTC";
	public static String regionTimeZoneId = "America/New_York";
	
	public String getRegionTime(String dateStr) {
		String regionDateTime = "";
		TimeZone zone = TimeZone.getTimeZone("America/New_York");
		SimpleDateFormat utcDF = new SimpleDateFormat(commonDateFormatStr);
		SimpleDateFormat regionDF = new SimpleDateFormat(commonDateFormatStr);
		regionDF.setTimeZone(zone);
		try {
			regionDateTime = regionDF.format(utcDF.parse(dateStr));
		} catch (ParseException e) {
			System.out.println("BSROAlignmentEdgeReportFunctionHandler : getRegionTime() : ParseException : " + e.getMessage());
		}
		return regionDateTime;
	}
	
	public static String getReportTime(String dateStr) {
		String reportingDateTime = "";
        LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(commonDateFormatStr));
        ZoneId singaporeZoneId = ZoneId.of(regionTimeZoneId);
        ZonedDateTime asiaZonedDateTime = ldt.atZone(singaporeZoneId);
        
        ZoneId newYokZoneId = ZoneId.of(utcTimeZoneId);
        ZonedDateTime nyDateTime = asiaZonedDateTime.withZoneSameInstant(newYokZoneId);
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern(commonDateFormatStr);
		reportingDateTime = format.format(nyDateTime);
		
		return reportingDateTime;
	}
	
	public static Date getRptTime(String dateStr) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
        return new Date();
	}
}
