package storehour;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonUnwrapped;

public class StoreHoliday1 {
	@JsonUnwrapped
    private StoreHolidayId1 id;

    private Long holidayId;

	private String description;
	
	private String status;
	private String day;
	private String month;
	private String year;

	@JsonIgnore
    public String getStatus() {
		return status;
	}
	
	@JsonIgnore
	public void setStatus(String status) {
		this.status = status;
	}
	
	public StoreHoliday1() {
    }
	/** constructor with id */
    public StoreHoliday1(StoreHolidayId1 id) {
        this.id = id;
    }
    
    public StoreHolidayId1 getId() {
        return this.id;
    }
    
    public void setId(StoreHolidayId1 id) {
        this.id = id;
    }
   
    public Long getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(Long holidayId) {
		this.holidayId = holidayId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "StoreHoliday1 [id=" + id + ", holidayId=" + holidayId
				+ ", description=" + description + ", status=" + status
				+ ", day=" + day + ", month=" + month + ", year=" + year + "]";
	}

}
