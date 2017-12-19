package samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.ConnectionClosedException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.opencsv.CSVReader;

import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

public class CSVReaderFile {
	
	private static final String bucketName="bsrodatamigration";
	private static final String csvfile="seo.csv";
	
	private static List<String> headers = new ArrayList<String>();
	private static List<String> types = new ArrayList<String>();
	
	public static void main(String[] args) {
		BasicAWSCredentials basicCred = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF");
		AmazonDynamoDBClient dyndbclient = new AmazonDynamoDBClient(basicCred);
		ClientConfiguration clientCfg = new ClientConfiguration();
		clientCfg.setProtocol(Protocol.HTTP);
		clientCfg.setProxyPort(443);
		AmazonS3 s3 = new AmazonS3Client(basicCred,clientCfg);
		DynamoDB dynamoDB = new DynamoDB(dyndbclient);
	    try {
			readRecords(s3, dynamoDB);
		} catch (ConnectionClosedException e) {
			e.printStackTrace();
		}
	}

	private static void readRecords(AmazonS3 s3, DynamoDB dynamoDB) throws ConnectionClosedException {
//		List<Developers> list = new ArrayList<Developers>();
		try {
			S3Object s3object = s3.getObject(new GetObjectRequest(bucketName, csvfile));
			CSVReader reader = new CSVReader(new InputStreamReader(s3object.getObjectContent(),"utf-8"));
			
			String records[];
			int row=0;
			Table table = dynamoDB.getTable("SeoVehicleData");
			while((records = reader.readNext()) != null) {
				if(row==0){
					for(String header : records){
						headers.add(header);
					}
				}else if(row==1){
					for(String type : records){
						types.add(type);
					}
				}else{
					Item item  = new Item();
					for(int i=0;i<records.length;i++){
						if(i==0){
							System.out.println("Primary "+" type "+types.get(i)+" "+headers.get(i) + "-->"+records[i]);
							item.withPrimaryKey(headers.get(i), Long.valueOf(records[i]));
						}else if(records[i]!= null && !records[i].isEmpty()){
							System.out.println("type "+types.get(i)+" "+headers.get(i) + "-->"+records[i]);
							switch(types.get(i)){
								case "S":
									item.withString(headers.get(i), records[i]);
									break;
								case "N":
									item.withNumber(headers.get(i), new BigInteger(records[i]));
									break;
								case "D":
									item.withNumber(headers.get(i), new BigDecimal(records[i]));
									break;
								case "B":
									item.withBoolean(headers.get(i), records[i].equalsIgnoreCase("true")?true:false);
									break;
							}
						}
						
					}
					System.out.println("item 1 "+item.toJSON());
					System.out.println("item 2 "+item.toString());
					System.out.println("item 3 "+item.toJSONPretty());
					PutItemOutcome outcome = table.putItem(item);
				}
				row++;
//				reader.close();
			}
			reader.close();
			s3object.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*private static void saveData(TableWriteItems writeRecords, DynamoDB dynamoDB) {
		try{
		BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(writeRecords);
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}
