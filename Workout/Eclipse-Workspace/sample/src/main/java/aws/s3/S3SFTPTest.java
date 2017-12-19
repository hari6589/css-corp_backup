package aws.s3;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class S3SFTPTest {

	public static void main(String[] args) throws JSchException, SftpException, IOException {
		String bucketName = "bridgestone-edge-report";
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.DATE, -1);
		String reportDate = "2017-08-15"; // dateFormatter.format(cal.getTime());
		
		String serverName = "52.44.78.71";
		String userName = "sganesan";
		String password = "password123";
		int portNumber = 22;
		
		JSch jsch = new JSch();
		Session session = null;
		ChannelSftp channel = null;
		session = jsch.getSession(userName, serverName, portNumber);
		session.setPassword(password);
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
            		   channel.put(objectData, objectSummary.getKey());
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
	}

}
