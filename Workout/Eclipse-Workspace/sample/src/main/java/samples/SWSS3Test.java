package samples;

import java.io.File;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.identitymanagement.model.User;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class SWSS3Test {
	
	public static AmazonS3Client amazonS3Client = null;
	
	public static void main(String[] args) {
		//System.out.println("Accessing CSS Account!");
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF"); // CSS Acc "AIDAJY5IBV44BR3UE5NZW"
		
		//System.out.println("Accessing BSRO Account!");
		//AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJAMYTZV465P467YA", "OuOr9+7aqa37P7Z6p20bnPYCNAlB2KxFzNaryadh"); // BSRO Acc "rzrfBsroMicroservices"
		
		amazonS3Client = new AmazonS3Client(awsCredentials);
		
		/*try {
			AmazonIdentityManagementClient iamClient = new AmazonIdentityManagementClient(awsCredentials);
			System.out.println(iamClient.getUser().getUser().getUserName());
			System.out.println(iamClient.getUser().getUser().getArn());
		} catch(Exception e) {
			System.err.println("Exception : " + e.getMessage() + " :: ");
		}*/
		
		try {
			
			String sourceDir = "D:\\AWS\\Workspace\\MaintenanceHistorySearch.zip";
			String fileName = "MaintenanceHistorySearch_V1.zip";
			String bucketName = "css-maintenance-history-legacy";
			
			if(checkBucket(bucketName)) {
				File file = new File(sourceDir);
				amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
				System.out.println("File Transfered!");
			} else {
				System.out.format("Bucket '%s' is not reachable!\n", bucketName);
			}
			
			/*
			File file = new File(sourceDir);
			TransferManager tm = new TransferManager(amazonS3Client);
		    MultipleFileUpload upload = tm.uploadDirectory(bucketName, fileName, file, true);
		    System.out.println("Transfer Triggered..!");
		    */
		    
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static boolean checkBucket(String bucketName) {
		System.out.println("checking bucket...");
		if (amazonS3Client.doesBucketExist(bucketName)) {
		    System.out.format("Bucket '%s' is already exists.\n", bucketName);
		} else {
		    try {
		    	Bucket bucket = amazonS3Client.createBucket(bucketName);
		    	System.out.format("Bucket '%s' is created!\n", bucket.getName());
		    } catch (AmazonS3Exception e) {
		        System.err.println("AmazonS3Exception : " + e.getMessage());
		        return false;
		    } catch (Exception e) {
		    	System.err.println("Exception : " + e.getMessage());
		        return false;
		    }
		}
		return true;
	}
}
