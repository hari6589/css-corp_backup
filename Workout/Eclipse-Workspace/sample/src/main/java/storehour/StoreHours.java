package storehour;

import java.util.List;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="StoreHours")
public class StoreHours {
	private Long storeNumber;
	private String siteName;
	private String storeType;
    private List<Hours> hours;
	private List<Holidays> holidays;
	private List<HolidayHours> holidayHours;
	
	@DynamoDBHashKey(attributeName="storeNumber") 
	public Long getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(Long storeNumber) {
		this.storeNumber = storeNumber;
	}
	
	@DynamoDBRangeKey(attributeName="siteName")
	@DynamoDBIndexHashKey(globalSecondaryIndexName = "storesHoursBySiteName-index", attributeName="siteName")
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	@DynamoDBIndexHashKey(globalSecondaryIndexName = "storesHoursByStoreType-index", attributeName="storeType")
	@DynamoDBAttribute(attributeName="storeType")
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	@DynamoDBAttribute(attributeName="hours")
	public List<Hours> getHours() {
		return hours;
	}
	public void setHours(List<Hours> hours) {
		this.hours = hours;
	}
	
	@DynamoDBAttribute(attributeName="holidays")
	public List<Holidays> getHolidays() {
		return holidays;
	}
	public void setHolidays(List<Holidays> holidays) {
		this.holidays = holidays;
	}
	
	@DynamoDBAttribute(attributeName="holidayHours")
	public List<HolidayHours> getHolidayHours() {
		return holidayHours;
	}
	public void setHolidayHours(List<HolidayHours> holidayHours) {
		this.holidayHours = holidayHours;
	}

	@DynamoDBDocument
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
		@Override
		public String toString() {
			return "Hours [weekDay=" + weekDay + ", openTime=" + openTime
					+ ", closeTime=" + closeTime + "]";
		}
		
	}
	
	@DynamoDBDocument
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
		@Override
		public String toString() {
			return "Holidays [year=" + year + ", month=" + month + ", day="
					+ day + ", holidayId=" + holidayId + ", description="
					+ description + "]";
		}
		
	}
	
	@DynamoDBDocument
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
		@Override
		public String toString() {
			return "HolidayHours [holidayId=" + holidayId + ", openTime="
					+ openTime + ", closeTime=" + closeTime + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "StoreHours [storeNumber=" + storeNumber + ", siteName="
				+ siteName + ", storeType=" + storeType + ", hours=" + hours
				+ ", holidays=" + holidays + ", holidayHours=" + holidayHours
				+ "]";
	}
	
	
	
}
