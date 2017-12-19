package aws.s3;

import java.io.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class ReadFileContent {

	public static void main(String[] args) {
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "AvHHbHqg6Ss8j7U6t9d5Y+uQpWKiGEMvv35onR1n87Bk");
		AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
		System.out.println(0);
		S3Object object = s3Client.getObject(new GetObjectRequest("css-mws-user-backup-local", "15080466816994.txt"));
		System.out.println(1);
		InputStream objectData = object.getObjectContent();
		System.out.println(objectData);
		
		
		
	}

}
