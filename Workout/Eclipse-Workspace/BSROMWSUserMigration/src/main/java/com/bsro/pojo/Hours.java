package com.bsro.pojo;

public class Hours {
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
	@Override
	public String toString() {
		return "Hours [weekDay=" + weekDay + ", openTime=" + openTime + ", closeTime=" + closeTime + "]";
	}
	
}
