package com.bsro;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;


import com.bsro.pojo.HolidayHours;
import com.bsro.pojo.Holidays;
import com.bsro.pojo.Hours;
import com.bsro.pojo.Stores;

public class StorHoursServices {
	private final int DAYS_PRIOR_TO_DISPLAY_HOLIDAY = 30;
	private final int MAX_HOLIDAYS_TO_DISPLAY = 2;
	
	public List<Stores> getStoreList(Connection connection) throws SQLException{
		List<Stores> stores = new ArrayList<Stores>();
		Statement stmt=connection.createStatement();  
//        ResultSet rs=stmt.executeQuery("select s.STORE_NUMBER storeNumber, bsm.SITE_NAME siteName, s.STORE_TYPE storeType from STORE s, BFRC_STORE_MAP bsm where s.STORE_TYPE = bsm.STORE_TYPE and bsm.SITE_NAME in ('FCAC', 'TP', 'HT', 'WWT')");
		  ResultSet rs=stmt.executeQuery("select s.STORE_NUMBER storeNumber, bsm.SITE_NAME siteName, s.STORE_TYPE storeType from STORE s, BFRC_STORE_MAP bsm where s.STORE_TYPE = bsm.STORE_TYPE and bsm.SITE_NAME in ('FCAC', 'TP', 'HT', 'WWT') and s.STORE_NUMBER='787742'");
        while(rs.next()){ 
        	Stores store = new Stores();
        	store.setStoreNumber(rs.getLong(1));
        	store.setStoreType(rs.getString(3));
        	store.setSiteName(rs.getString(2));
        	stores.add(store);
        }
        return stores;
	}
	
	public Map<Long,List<Hours>> getHours(Connection connection) throws SQLException{
		Map<Long,List<Hours>> hours = new HashMap<Long,List<Hours>>();
		Statement stmt=connection.createStatement();  
        ResultSet rs=stmt.executeQuery("select STORE_NUMBER,WEEK_DAY,OPEN_TIME,CLOSE_TIME from STORE_HOUR ORDER BY STORE_NUMBER");  
        long newStoreNo = 0;
        long oldStoreNo = 0;
        List<Hours> hoursListForStore = new ArrayList<Hours>();
        while(rs.next()){ 
        	newStoreNo = rs.getLong(1);
        	if(newStoreNo!=oldStoreNo && hoursListForStore.size()!=0 ){
        		hours.put(oldStoreNo,hoursListForStore);
        		hoursListForStore = new ArrayList<Hours>();
        	}
        	Hours h = new Hours();
        	h.setCloseTime(rs.getString(4));
        	h.setOpenTime(rs.getString(3));
        	h.setWeekDay(rs.getString(2));
        	hoursListForStore.add(h);
        	oldStoreNo = rs.getLong(1);
        }
        System.out.println("Hours Object "+hours);
        return hours;
	}
	
	public Map<Long,List<HolidayHours>> getHolidayHours(Connection connection) throws SQLException{
		Map<Long,List<HolidayHours>> holidayHours = new HashMap<Long,List<HolidayHours>>();
		Statement stmt=connection.createStatement();  
        ResultSet rs=stmt.executeQuery("select STORE_NUMBER,OPEN_TIME,CLOSE_TIME,HOLIDAY_ID from STORE_HOLIDAY_HOURS ORDER BY STORE_NUMBER");  
        long newStoreNo = 0;
        long oldStoreNo = 0;
        List<HolidayHours> holidayHoursListForStore = new ArrayList<HolidayHours>();
        
        while(rs.next()){ 
        	newStoreNo = rs.getLong(1);
        	if(newStoreNo!=oldStoreNo && holidayHoursListForStore.size()!=0){
        		holidayHours.put(oldStoreNo,holidayHoursListForStore);
        		holidayHoursListForStore = new ArrayList<HolidayHours>();
        	}
        	HolidayHours hh = new HolidayHours();
        	hh.setCloseTime(rs.getString(3));
        	hh.setOpenTime(rs.getString(2));
        	hh.setHolidayId(rs.getString(4));
        	holidayHoursListForStore.add(hh);
        	oldStoreNo = rs.getLong(1);
        }
        return holidayHours; 
	}
	
	public List<Holidays> getHolidays(Connection connection) throws SQLException, ParseException{
		
		Date startDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) +DAYS_PRIOR_TO_DISPLAY_HOLIDAY);
		Date endDate = cal.getTime();
		SimpleDateFormat yearSDF = new SimpleDateFormat("yyyy");
		String startYear = yearSDF.format(startDate);
		String endYear = yearSDF.format(endDate);
		
		GregorianCalendar gcStart = new GregorianCalendar();
		gcStart.setTime(startDate);
		gcStart.add(Calendar.DATE, -1);
		
		GregorianCalendar gcEnd = new GregorianCalendar();
		gcEnd.setTime(endDate);
		//12AM end day
		gcEnd.set(Calendar.HOUR_OF_DAY, 0);
		gcEnd.set(Calendar.MINUTE, 0);
		gcEnd.set(Calendar.SECOND, 0);
		gcEnd.set(Calendar.MILLISECOND, 0);
		gcEnd.set(Calendar.AM_PM, Calendar.AM);
		
		List<Holidays> holidays = new ArrayList<Holidays>();
		List<Holidays> storeHolidaysToReturn = new ArrayList<Holidays>();
		Statement stmt=connection.createStatement();  
        ResultSet rs=stmt.executeQuery("select YEAR,MONTH,DAY,DESCRIPTION,HOLIDAY_ID from STORE_HOLIDAY where YEAR between "+startYear+" and "+endYear +" ORDER BY YEAR,MONTH,DAY");  
        while(rs.next()){ 
        	Holidays hol = new Holidays();
        	hol.setYear(rs.getString(1));
        	hol.setMonth(rs.getString(2));
        	hol.setDay(rs.getString(3));
        	hol.setDescription(rs.getString(4));
        	hol.setHolidayId(rs.getString(5));
        	holidays.add(hol);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date gcEndDate = dateFormat.parse( (gcEnd.get(Calendar.MONTH) + 1) +"/"+(gcEnd.get(Calendar.DAY_OF_MONTH))+"/"+gcEnd.get(Calendar.YEAR));
        
    	if(holidays!=null){
    		int count = 1;
			for(Holidays sh:holidays){
				Date middleDate = dateFormat.parse(sh.getMonth() +"/"+sh.getDay()+"/"+sh.getYear());
				if(middleDate.after(gcStart.getTime()) && middleDate.before(gcEndDate) && count<=MAX_HOLIDAYS_TO_DISPLAY){
					storeHolidaysToReturn.add(sh);
					count++;
				}
			}		
		}
        return storeHolidaysToReturn; 
	}

}
