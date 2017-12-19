package com.bsro.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreHours {
	private Long storeNumber;
	private String siteName;
	private String storeType;
    private List<Hours> hours;
	private List<Holidays> holidays;
	private List<HolidayHours> holidayHours;
	
	public Long getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(Long storeNumber) {
		this.storeNumber = storeNumber;
	}
	
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	public List<Hours> getHours() {
		return hours;
	}
	public void setHours(List<Hours> hours) {
		this.hours = hours;
	}
	
	public List<Holidays> getHolidays() {
		return holidays;
	}
	public void setHolidays(List<Holidays> holidays) {
		this.holidays = holidays;
	}
	
	public List<HolidayHours> getHolidayHours() {
		return holidayHours;
	}
	public void setHolidayHours(List<HolidayHours> holidayHours) {
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
