package bro;

import java.util.Iterator;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class bucketDelete {

	public static void main(String[] args) {

		final String endPoint = "https://kr.object.ncloudstorage.com";
		final String regionName = "kr-standard";
		final String accessKey = "MKwuKCAF13r99MQQUFII";
		final String secretKey = "UkoSntgAHO17mlwMFVWwNVaxKjW8VxUoHYr8yiu2";

		// S3 client
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
		    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
		    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
		    .build();

		String bucketName = "goo12";

		try {
		    // delete bucket if the bucket exists
		    if (s3.doesBucketExistV2(bucketName)) {
		        // delete all objects
		        ObjectListing objectListing = s3.listObjects(bucketName);
		        while (true) {
		            for (Iterator<?> iterator = objectListing.getObjectSummaries().iterator(); iterator.hasNext();) {
		                S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
		                s3.deleteObject(bucketName, summary.getKey());
		            }

		            if (objectListing.isTruncated()) {
		                objectListing = s3.listNextBatchOfObjects(objectListing);
		            } else {
		                break;
		            }
		        }

		        // abort incomplete multipart uploads
		        MultipartUploadListing multipartUploadListing = s3.listMultipartUploads(new ListMultipartUploadsRequest(bucketName));
		        while (true) {
		            for (Iterator<?> iterator = multipartUploadListing.getMultipartUploads().iterator(); iterator.hasNext();) {
		                MultipartUpload multipartUpload = (MultipartUpload)iterator.next();
		                s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, multipartUpload.getKey(), multipartUpload.getUploadId()));
		            }

		            if (multipartUploadListing.isTruncated()) {
		                ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
		                listMultipartUploadsRequest.withUploadIdMarker(multipartUploadListing.getNextUploadIdMarker());
		                multipartUploadListing = s3.listMultipartUploads(listMultipartUploadsRequest);
		            } else {
		                break;
		            }
		        }

		        s3.deleteBucket(bucketName);
		        System.out.format("Bucket %s has been deleted.\n", bucketName);
		    } else {
		        System.out.format("Bucket %s does not exist.\n", bucketName);
		    }
		} catch (AmazonS3Exception e) {
		    e.printStackTrace();
		} catch(SdkClientException e) {
		    e.printStackTrace();
		}
	}

}
