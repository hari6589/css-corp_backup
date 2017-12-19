package samples;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String st = "Mon, 28 Oct 2017 13:00:00 CDT";
		System.out.println(st);
		
		String sp[] = st.split(" ");
		
//		System.out.println(sp[1]);
//		System.out.println(sp[2]);
//		System.out.println(getMonthInt(sp[2]));
//		System.out.println(sp[3]);
//		System.out.println(sp[4].substring(0, sp[4].lastIndexOf(":")));
		
		//"yyyyMMdd HH:mm"
		String dateStr = sp[3]+getMonthInt(sp[2])+sp[1]+" "+sp[4].substring(0, sp[4].lastIndexOf(":"));
		System.out.println(dateStr);
		
//		int[] num = {3, 2, 1, 1, 1, 1, 1};
//		int ls = 0;
//		int rs = 0;
//		int li = num.length - 1;
//		int si = 0;
//		while(true) {
//			if(ls > rs) {
//				rs += num[si++];
//			} else {
//				ls += num[li--];
//			}
//			System.out.println(si + " _ " + li + " = " + ls + " _ " + rs);
//			if(li == si) {
//				if(ls == rs) {
//					System.out.println("Equal Sum : " + ls + " _ " + rs);
//				} else {
//					System.out.println("Invalid input!");
//				}
//				break;
//			}
//		}
		//ts();
	}
	
	public static String getMonthInt(String monthStr) {
		Map<String, String> monthMap = new HashMap<String, String>();
		monthMap.put("Jan", "01");
		monthMap.put("Feb", "02");
		monthMap.put("Mar", "03");
		monthMap.put("Apr", "04");
		monthMap.put("May", "05");
		monthMap.put("Jun", "06");
		monthMap.put("Jul", "07");
		monthMap.put("Aug", "08");
		monthMap.put("Sep", "09");
		monthMap.put("Oct", "10");
		monthMap.put("Nov", "11");
		monthMap.put("Dec", "12");
		return monthMap.get(monthStr);
	}
	
	public static void ts() {
		// 5:30
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		
		long l = 1507210699383L;
		Date d = new Date(l);
		formatter.setTimeZone(TimeZone.getTimeZone("IST"));
		System.out.println(formatter.format(d));
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		System.out.println(formatter.format(d));
	}
}
