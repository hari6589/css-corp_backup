package tirequote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Store {
	private Long storeNumber;
	private String siteName;
	private String storeName;
	private String address;
	private String city;
	@JsonProperty("states")
	private String states;
	private String zip;
	private String phone;
	private String trackingPhone;
	private String managerName;
	private String managerEmailAddress;
	private String storeType;
	private int activeFlag;
	private String latitude;
	private String longitude;
	private int onlineAppointmentActiveFlag;
	private int tirePricingActiveFlag;
	private String fax;
	private String localPageURL;
	private int eCommActiveFlag;
	private String areaId;
	private Integer distance;
	
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
	
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return states;
	}
	public void setState(String states) {
		this.states = states;
	}
	
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getTrackingPhone() {
		return trackingPhone;
	}
	public void setTrackingPhone(String trackingPhone) {
		this.trackingPhone = trackingPhone;
	}
	
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	public String getManagerEmailAddress() {
		return managerEmailAddress;
	}
	public void setManagerEmailAddress(String managerEmailAddress) {
		this.managerEmailAddress = managerEmailAddress;
	}
	
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	public int getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public int getOnlineAppointmentActiveFlag() {
		return onlineAppointmentActiveFlag;
	}
	public void setOnlineAppointmentActiveFlag(int onlineAppointmentActiveFlag) {
		this.onlineAppointmentActiveFlag = onlineAppointmentActiveFlag;
	}
	
	public int getTirePricingActiveFlag() {
		return tirePricingActiveFlag;
	}
	public void setTirePricingActiveFlag(int tirePricingActiveFlag) {
		this.tirePricingActiveFlag = tirePricingActiveFlag;
	}
	
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getLocalPageURL() {
		return localPageURL;
	}
	public void setLocalPageURL(String localPageURL) {
		this.localPageURL = localPageURL;
	}
	
	public int geteCommActiveFlag() {
		return eCommActiveFlag;
	}
	public void seteCommActiveFlag(int eCommActiveFlag) {
		this.eCommActiveFlag = eCommActiveFlag;
	}
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
}
