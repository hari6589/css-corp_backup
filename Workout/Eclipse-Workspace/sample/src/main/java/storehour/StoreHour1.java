package storehour;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonUnwrapped;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class StoreHour1 {

	@JsonUnwrapped
	private StoreHourId1 id;
	private String openTime;
	private String closeTime;
	private String timeZone;
	private String weekDay;
	
	public StoreHour1() {
	}

	public StoreHour1(StoreHourId1 id) {
		this.id = id;
	}

	public StoreHourId1 getId() {
		return this.id;
	}

	public void setId(StoreHourId1 id) {
		this.id = id;
	}

	public String getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getTimeZone() {
		return this.timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreHour1 other = (StoreHour1) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StoreHour1 [id=" + id + ", openTime=" + openTime
				+ ", closeTime=" + closeTime + ", timeZone=" + timeZone + "]";
	}

	
}