package storehour;

import java.util.List;
import java.util.Set;

public class StoreHourMas {

	private String siteName;
	private String storeType;
	private String storeNumber;
	private Set<StoreHour1> hours;
	private List<StoreHoliday1> holidays;
	
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

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public Set<StoreHour1> getHours() {
		return hours;
	}

	public void setHours(Set<StoreHour1> hours) {
		this.hours = hours;
	}

	public List<StoreHoliday1> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<StoreHoliday1> holidays) {
		this.holidays = holidays;
	}

	@Override
	public String toString() {
		return "StoreHourMas [siteName=" + siteName + ", storeType="
				+ storeType + ", storeNumber=" + storeNumber + ", hours="
				+ hours + ", holidays=" + holidays + "]";
	}
	
}
