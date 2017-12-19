package aws.s3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;

public class DeleteObject {

	public static void main(String[] args) {
		AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAIO6KVA4ID6VFURAA", "aaSNiXJVyTafsNBJfuLXp0+/KeiPbUUeel54XcKF");
		AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
		
		DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest("var-test");
		List<KeyVersion> keys = new ArrayList<KeyVersion>();
		keys.add(new KeyVersion("one.txt"));
		keys.add(new KeyVersion("two.txt"));
		multiObjectDeleteRequest.setKeys(keys);
		DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
	    System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
	}

}
