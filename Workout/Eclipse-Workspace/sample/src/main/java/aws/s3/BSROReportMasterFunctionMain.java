package aws.s3;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class BSROReportMasterFunctionMain {
	public static Properties properties = null;
	public static AWSCredentials awsCredentials = null;
	public static AmazonS3Client amazonS3Client = null;
	
	public static void main(String[] args) {
		
		InputStream inputStream = null;
		String environment = "";
		try {
			environment = "local";
			BSROReportMasterFunctionMain em = new BSROReportMasterFunctionMain(); 
			inputStream = em.getResourceStream(environment);
			properties = new Properties();
			properties.load(inputStream);
			System.out.println(properties.getProperty("environment"));
			awsCredentials = new BasicAWSCredentials(properties.getProperty("accessKey"), properties.getProperty("secretKey"));
			amazonS3Client = new AmazonS3Client(awsCredentials);
		} catch (IOException e) {
			System.out.println("IOException in BSROReportMasterFunctionMain Constructor!");
		} catch (Exception e) {
			System.out.println("Exception in BSROReportMasterFunctionMain Constructor!");
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				System.out.println("Exception in BSROReportMasterFunctionMain Constructor!");
			}
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		//Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DATE, -1);
		String reportDate = "2017-08-08"; // dateFormatter.format(cal.getTime());
		
		String bucketName = properties.getProperty("aws.s3.bucket.name");
		String serverName = properties.getProperty("sftp.server.host");
		String username = properties.getProperty("sftp.server.username");
		String password = properties.getProperty("sftp.server.password");
		String portNumberStr = properties.getProperty("sftp.server.port");
		
		System.out.println(serverName);
		System.out.println(username);
		System.out.println(password);
		
		int portNumber = 22; // Default
		if(portNumberStr != null && !portNumberStr.equals("")) {
			portNumber = Integer.parseInt(portNumberStr);
		}
		
		JSch jsch = new JSch();
		Session session = null;
		ChannelSftp channel = null;
		
		try {
			session = jsch.getSession(username, serverName, portNumber);
			session.setPassword(password);
			 
			//Make it so we do not do host key checking. Enabling this would require some extra code and maintenance, but would increase security.
			session.setConfig("StrictHostKeyChecking", "no");
			session.setTimeout(15000);
			System.out.println("Connecting...");
			session.connect();
			System.out.println("Connected!"); 
			channel = (ChannelSftp)session.openChannel("sftp");
			channel.connect();
			System.out.println("Channel Connected!");
			
			AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
	        try {
	            System.out.println("Listing objects");
	            final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withMaxKeys(2);
	            ListObjectsV2Result result;
	            do {
	               result = s3Client.listObjectsV2(req);
	               for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
	            	   if(reportDate.equals(dateFormatter.format(objectSummary.getLastModified()))) {
	            		   System.out.println(objectSummary.getBucketName() + " / " + objectSummary.getKey() + " / " + objectSummary.getLastModified() + " / " + objectSummary.getSize());
	            		   S3Object object = s3Client.getObject(new GetObjectRequest(objectSummary.getBucketName(), objectSummary.getKey()));
	            		   InputStream objectData = object.getObjectContent();
	            		   System.out.println(objectSummary.getBucketName() + " --- " + objectSummary.getKey());
	            		   //channel.put(objectData, objectSummary.getKey());
	            		   System.out.println("File copied!");
	            		   objectData.close();
	                   }
	               }
	               //System.out.println("Next Continuation Token : " + result.getNextContinuationToken());
	               req.setContinuationToken(result.getNextContinuationToken());
	            } while(result.isTruncated() == true );
	            
	         } catch (AmazonServiceException ase) {
	            System.out.println("Caught an AmazonServiceException, " +
	            		"which means your request made it " +
	                    "to Amazon S3, but was rejected with an error response " +
	                    "for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
	            System.out.println("Caught an AmazonClientException, " +
	            		"which means the client encountered " +
	                    "an internal error while trying to communicate" +
	                    " with S3, " +
	                    "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        }
	        
			channel.disconnect();
			
			System.out.println("SUCCESS");
		} catch(Exception e) {
			System.out.println("Exception : " + e.getMessage() + " ::: " + e.getStackTrace());
			System.out.println("FAILURE");
		}
	}
	
	public InputStream getResourceStream(String environment) {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(environment + "//application.properties");
		return inputStream;
	}
}
