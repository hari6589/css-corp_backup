package com.bsro.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class StoreHoursDynamoJSON {
	
	Map<String, AttributeValue> storeNumber = new HashMap<String, AttributeValue>();
	Map<String, AttributeValue> siteName = new HashMap<String, AttributeValue>();
	Map<String, AttributeValue> storeType = new HashMap<String, AttributeValue>();
	Map<List<Hours>, AttributeValue> hours = new HashMap<List<Hours>, AttributeValue>();
	Map<List<Holidays>, AttributeValue> holidays = new HashMap<List<Holidays>, AttributeValue>();
	Map<List<HolidayHours>, AttributeValue> holidayHours = new HashMap<List<HolidayHours>, AttributeValue>();

	
	public Map<String, AttributeValue> getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(Map<String, AttributeValue> storeNumber) {
		this.storeNumber = storeNumber;
	}

	public Map<String, AttributeValue> getSiteName() {
		return siteName;
	}

	public void setSiteName(Map<String, AttributeValue> siteName) {
		this.siteName = siteName;
	}

	public Map<String, AttributeValue> getStoreType() {
		return storeType;
	}

	public void setStoreType(Map<String, AttributeValue> storeType) {
		this.storeType = storeType;
	}

	public Map<List<Hours>, AttributeValue> getHours() {
		return hours;
	}

	public void setHours(Map<List<Hours>, AttributeValue> hours) {
		this.hours = hours;
	}

	public Map<List<Holidays>, AttributeValue> getHolidays() {
		return holidays;
	}

	public void setHolidays(Map<List<Holidays>, AttributeValue> holidays) {
		this.holidays = holidays;
	}

	public Map<List<HolidayHours>, AttributeValue> getHolidayHours() {
		return holidayHours;
	}

	public void setHolidayHours(Map<List<HolidayHours>, AttributeValue> holidayHours) {
		this.holidayHours = holidayHours;
	}

	

	 public static class Hours {

		private String weekDay;
		private String openTime;
		private String closeTime;
		
		public String getWeekDay() {
			return weekDay;
		}
		public void setWeekDay(String weekDay) {
			this.weekDay = weekDay;
		}
		
		
		public String getOpenTime() {
			return openTime;
		}
		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}
		
		
		public String getCloseTime() {
			return closeTime;
		}
		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}
		
	}
	
	public static class Holidays {
		private String year;
		private String month;
		private String day;
		private String holidayId;
		private String description;
		
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		
		public String getHolidayId() {
			return holidayId;
		}
		public void setHolidayId(String holidayId) {
			this.holidayId = holidayId;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public static class HolidayHours {
		private String holidayId;
		private String openTime;
		private String closeTime;
		
		public String getHolidayId() {
			return holidayId;
		}
		public void setHolidayId(String holidayId) {
			this.holidayId = holidayId;
		}
		
		public String getOpenTime() {
			return openTime;
		}
		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}
		
		public String getCloseTime() {
			return closeTime;
		}
		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}
	}

}
