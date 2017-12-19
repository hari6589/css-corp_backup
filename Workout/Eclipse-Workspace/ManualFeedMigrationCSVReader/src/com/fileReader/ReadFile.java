package com.fileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.servicecatalog.model.ResourceNotFoundException;
import com.opencsv.CSVReader;

public class ReadFile {
	private static List<String> headers = new ArrayList<String>();
	private static List<String> types = new ArrayList<String>();
	private static Table table;
	private static File directory = new File("D:\\AWS\\Source\\Inputs\\");
	private static String accessKey;
	private static String secretKey;
	private static DynamoDB dynamoDB;
	private static AmazonDynamoDBClient dyndbclient;
	static List<String> path = ReadFile.listFilesForFolder(directory);
	private static String jsonData = "jsonData";
	private static String jsonDataType = "JD";
	
	public static void readFile(DynamoDB dynamoDB, Table table, String path) throws Exception {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(new AmazonDynamoDBClient(awsCredentials));
		AmazonS3Client amazonS3Client = new AmazonS3Client(awsCredentials);
		
		try {
			System.out.println("Processing > " + path);
			CSVReader reader = new CSVReader(new FileReader(path), ',', '"');
			String[] records;
			int row = 0;
			while ((records = reader.readNext()) != null) {
				if (row == 0) {
					for (String header : records) {
						headers.add(header);
					}
				} else if (row == 1) {
					for (String type : records) {
						types.add(type);
					}
				} else {
					Item item = new Item();
					long id = 0L;
					for (int i = 0; i < records.length; i++) {
						if(i == 0) {
							id = Long.valueOf(records[i]);
						}
						if((!headers.get(i).equals(jsonData) && !types.get(i).equals(jsonDataType))) {
							if (records[i] != null && !records[i].isEmpty()) {
								switch (types.get(i)) {
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
										item.withBoolean(headers.get(i), records[i].equalsIgnoreCase("true") ? true: false);
										break;
								}
							}
						} else {
							if(id != 0L) {
								if(records[i] != null && !records[i].equals("")) {
									String fileName = String.valueOf(id) + ".txt";
									File file = Files.createTempFile(fileName, "txt", new FileAttribute[0]).toFile();
							    	
							    	FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
							    	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
							    	bufferedWriter.append(records[i]);
							    	bufferedWriter.close();
							    	amazonS3Client.putObject(new PutObjectRequest("bridgestone-mws-user-local", fileName, file));
							    	fileWriter.close();
						            file.delete();
								} else {
									System.out.println("No backup found for the user : " + id);
								}
							} else {
								System.out.println("Unable to backup for the user_csv-row-index : " + row);
							}
						}
					}

					try {
						PutItemOutcome outcome = table.putItem(item);
					} catch (Exception e) {
						e.printStackTrace();
						throw new ResourceNotFoundException(table.getTableName() + " Table doesn't Exist.");
					}
					
				}
				//System.out.println("Row : " + row);
				row++;
			}
			reader.close();
			headers.clear();
			types.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> listFilesForFolder(File folder) {
		System.out.println("List of files in folder......");
		int count = 0;
		List<String> filenames = new LinkedList<String>();
		List<String> listFile = new LinkedList<String>();
		if (count == 0) {
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					listFilesForFolder(fileEntry);
				} else {
					if (fileEntry.getName().contains(".csv")
							&& (!fileEntry.isDirectory()))
						filenames.add(fileEntry.getName());
					listFile.add(fileEntry.getAbsolutePath());
					System.out.println("FILE====>>>>" + fileEntry.getName());
					System.out.println("FILE PATH =====>>>"
							+ fileEntry.getAbsolutePath());
				}
				count++;
			}
		}
		return listFile;
	}

	public static List<String> getTableName() {
		System.out.println("Table names Method ...");
		List<String> tableList = new ArrayList<String>();
		List<String> tableNames = new ArrayList<String>();
		String f = null;
		tableList.addAll(path);
		for (int i = 0; i < tableList.size(); i++) {
			f = FilenameUtils.getBaseName(tableList.get(i));
			// tableNames.add(f.substring(0, 4));
			tableNames.add(f);
		}
		return tableNames;
	}

	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream fileIO;
		try {
			fileIO = new FileInputStream("config.properties");
			try {
				prop.load(fileIO);
				accessKey = prop.getProperty("accessKey");
				secretKey = prop.getProperty("secretKey");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BasicAWSCredentials basicCred = new BasicAWSCredentials(accessKey, secretKey);
		dyndbclient = new AmazonDynamoDBClient(basicCred);
		dynamoDB = new DynamoDB(dyndbclient);
		ClientConfiguration clientCfg = new ClientConfiguration();
		clientCfg.setMaxErrorRetry(10);
		clientCfg.setProtocol(Protocol.HTTP);
		clientCfg.setProxyPort(443);
		clientCfg.setConnectionTimeout(5 * 1000);
		clientCfg.setSocketTimeout(5 * 1000);

		List<String> tablename = getTableName();
		int x;

		for (x = 0; x < tablename.size(); x++) {
			for (int y = 0; y < path.size(); y++, x++) {
				System.out.println("Table Name " + tablename);
				table = new Table(dyndbclient, tablename.get(x));
				table = dynamoDB.getTable(tablename.get(x));
				try {
					ReadFile.readFile(dynamoDB, table, path.get(y));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
