package com.appointment.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="AppointmentService")
public class AppointmentService {
	private int serviceId;
	private String serviceDesc;
	private String serviceCategory;
	private String vehicleRequiredInd;
	private int serviceType;
	private int sortOrder;
	
	@DynamoDBHashKey(attributeName="ServiceId")
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	
	@DynamoDBAttribute(attributeName="ServiceDesc")
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	
	@DynamoDBAttribute(attributeName="ServiceCategory")
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	
	@DynamoDBAttribute(attributeName="VehicleRequiredInd")
	public String getVehicleRequiredInd() {
		return vehicleRequiredInd;
	}
	public void setVehicleRequiredInd(String vehicleRequiredInd) {
		this.vehicleRequiredInd = vehicleRequiredInd;
	}
	
	@DynamoDBAttribute(attributeName="ServiceType")
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	
	@DynamoDBAttribute(attributeName="SortOrder")
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
