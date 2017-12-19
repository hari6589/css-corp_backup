package samples.pojo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="GeoTrigger")
public class GeoTrigger {
	
	private long id;
	private String createdDate;
	private int activeFlag;
	
	@DynamoDBHashKey(attributeName="id")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@DynamoDBAttribute(attributeName="createdDate")
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	@DynamoDBAttribute(attributeName="activeFlag")
	public int getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	
}