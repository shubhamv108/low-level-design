package code.shubham.blob.stores;

import code.shubham.commonexceptions.AWSS3BlobStoreException;
import code.shubham.commons.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component("AWSS3BlobStore")
public class AWSS3BlobStore implements IBlobStore {

    @Value("${aws.region: ap-south-1}")
    private String region;

    private AwsCredentials credentials;
    private final S3Client client;
    private final S3Presigner preSigner;
    private final Map<Region, S3Client> regionS3Clients = new ConcurrentHashMap<>();

    @Autowired
    public AWSS3BlobStore(AwsCredentials credentials) {
        this.credentials = credentials;
        this.client = S3Client.builder().
                credentialsProvider(() -> credentials).
                region(Region.of(region)).
                build();
        this.preSigner = S3Presigner.builder().
                credentialsProvider(() -> credentials).
                region(Region.of(region)).
                build();
    }

    private S3Client getS3Client(Region region) {
        return this.getS3Client(region, this.credentials);
    }

    private S3Client getS3Client(Region region, AwsCredentials credentials) {
        S3Client client = this.regionS3Clients.get(region);
        if (client != null)
            return client;
        synchronized (region.id()) {
            client = this.regionS3Clients.get(region);
            if (client != null)
                return client;
            client = S3Client.builder().
                    credentialsProvider(() -> credentials).
                    region(region).
                    build();
            return client;
        }
    }

    @Override
    public boolean createBucketIfNotExists(String bucketName) {
        boolean isCreated = false;
        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            this.client.createBucket(createBucketRequest);

            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            S3Waiter s3Waiter = this.client.waiter();
            // Wait until the bucket is created and print out the response.
            WaiterResponse<HeadBucketResponse> waiterResponse =
                    s3Waiter.waitUntilBucketExists(bucketRequestWait);
            isCreated = waiterResponse.matched().response().map(response -> true).
                    orElse(false);
        } catch (BucketAlreadyExistsException | BucketAlreadyOwnedByYouException exception) {
            log.info("Bucket with name: %s, Already exists");
        } catch (SdkClientException exception) {
            throw new AWSS3BlobStoreException(
                    region, "Creating Bucket: " + bucketName, exception);
        } catch (S3Exception exception) {
            throw new AWSS3BlobStoreException(
                    region, "Creating Bucket: " + bucketName, exception);
        }
        return isCreated;
    }

    @Override
    public String getUploadPreSignedUrl(String bucketName, String objectName, String contentType, long timeToLiveInMilliseconds, Map<String, String> metadata) {
        PutObjectRequest objectRequest = PutObjectRequest.builder().
                bucket(bucketName).
                key(objectName).
                contentType(contentType).
                metadata(metadata).
                build();

        PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder().
                signatureDuration(Duration.ofMillis(timeToLiveInMilliseconds)).
                putObjectRequest(objectRequest).
                build();

        PresignedPutObjectRequest preSignedRequest = this.preSigner.presignPutObject(preSignRequest);
        return preSignedRequest.url().toString();
    }

    @Override
    public DownloadPreSignedUrlResponse getDownloadPreSignedUrl(String bucketName, String objectKeyName, String contentType, long timeToLiveInMilliseconds, Map<String, String> metadata) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKeyName)
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMillis(timeToLiveInMilliseconds))
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest =
                    this.preSigner.presignGetObject(getObjectPresignRequest);
            String preSignedUrl = presignedGetObjectRequest.url().toString();
            Map<String, List<String>> headers = new LinkedHashMap<>();
            presignedGetObjectRequest.httpRequest().headers().forEach(headers::put);
            return DownloadPreSignedUrlResponse.builder().
                    headers(headers).
                    url(preSignedUrl).
                    build();
        }  catch (S3Exception exception) {
            throw new AWSS3BlobStoreException(
                    region, String.format("GET PreSignedUrl: s3://%s/%s", bucketName, objectKeyName), exception);
        }
    }

    @Override
    public String uploadFile(String bucketName, String objectKeyName, String fileName, String contentType, Map<String, String> userMetadata) {
        try {
            if (StringUtils.isEmpty(bucketName))
                throw new IllegalArgumentException("Empty bucket name");
            if (StringUtils.isEmpty(objectKeyName))
                throw new IllegalArgumentException("Empty file object key name");
            if (StringUtils.isEmpty(fileName))
                throw new IllegalArgumentException("Empty file name");

            log.info("Uploading file: s3://%s/%s", fileName, bucketName, objectKeyName);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().
                    bucket(bucketName).
                    key(objectKeyName).
                    metadata(userMetadata).
                    build();
            if (StringUtils.isEmpty(contentType))
                userMetadata.put("Content-Type", contentType);

            PutObjectResponse response = this.client.putObject(
                    putObjectRequest, RequestBody.fromFile(new File(fileName)));
//            String url = this.client.utilities().getUrl(builder -> builder.bucket(bucketName).key(objectKeyName)).toExternalForm();
            return response.eTag();
        } catch (SdkClientException exception) {
            throw new AWSS3BlobStoreException(
                    region, "PUT Object: s3://%s/%s" + bucketName, exception);
        } catch (S3Exception exception) {
            throw new AWSS3BlobStoreException(
                    region, "PUT Object: s3://%s/%s" + bucketName, exception);
        }
    }

    @Override
    public Path downloadFile(String bucketName, String objectKeyName, String filePath) throws IOException {
        Path downloadFilePath = null;
        try {
            downloadFilePath = Paths.get(filePath);
            GetObjectRequest objectRequest = GetObjectRequest
                    .builder()
                    .bucket(bucketName)
                    .key(objectKeyName)
                    .responseCacheControl("No-cache")
                    .responseContentDisposition("attachment; filename=" + filePath)
                    .build();
            log.info("Downloading File: s3://%s/%s", bucketName, objectKeyName);
            ResponseBytes<GetObjectResponse> objectBytes = this.client.getObjectAsBytes(objectRequest);
            log.info("Downloaded File: s3://%s/%s", bucketName, objectKeyName);
            downloadFilePath.getParent().toFile().mkdirs();

            log.info("Writing downloaded file to: %s", downloadFilePath.toUri());
            Files.copy(objectBytes.asInputStream(), downloadFilePath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Written downloaded file to: %s", downloadFilePath.toUri());
        } catch (IOException ioException) {
            log.info(String.format("Error while writing downloaded input stream to file: %s", filePath));
            throw new AWSS3BlobStoreException(
                    region, "GET Object: s3://%s/%s" + bucketName, ioException);
        } catch (SdkClientException sdkClientException) {
            log.info("Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3.");
            throw new AWSS3BlobStoreException(
                    region, String.format("GET Object: s3://%s/%s", bucketName, objectKeyName), sdkClientException);
        } catch (S3Exception s3Exception) {
            log.info(String.format("Amazon S3Exception while GET Object: s3://%s/%s", bucketName, objectKeyName));
            throw new AWSS3BlobStoreException(
                    region, String.format("GET Object: s3://%s/%s", bucketName, objectKeyName), s3Exception);

        }
        return downloadFilePath;
    }

    @Override
    public void copy(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {
        try {
            CopyObjectRequest copyObjRequest = CopyObjectRequest.builder().
                    sourceBucket(sourceBucket).
                    sourceKey(sourceKey).
                    destinationBucket(destinationBucket).
                    destinationKey(destinationKey).
                    build();
            log.info(String.format("Copying from s3://%s/%s to s3://%s/%s", sourceBucket, sourceKey, destinationBucket, destinationKey));
            this.client.copyObject(copyObjRequest);
            log.info(String.format("Copied from s3://%s/%s to s3://%s/%s", sourceBucket, sourceKey, destinationBucket, destinationKey));
        } catch (SdkClientException sdkClientException) {
            log.info("Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3.");
            throw new AWSS3BlobStoreException(
                    region, String.format("cp s3://%s/%s s3://%s/%s", sourceBucket, sourceKey, destinationBucket, destinationKey), sdkClientException);
        } catch (S3Exception s3Exception) {
            log.info(String.format("cp s3://%s/%s s3://%s/%s", sourceBucket, sourceKey, destinationBucket, destinationKey));
            throw new AWSS3BlobStoreException(
                    region, String.format("cp s3://%s/%s s3://%s/%s", sourceBucket, sourceKey, destinationBucket, destinationKey), s3Exception);
        }
    }

//    public Transfer uploadFileAsync(String bucketName, String objectKeyName, String filePath) {
//        File fileToBeUploaded = new File(filePath);
//        TransferManager transferManager = TransferManagerBuilder.standard().build();
//        Upload upload;
//        try {
//            upload = transferManager.upload(bucketName, objectKeyName, fileToBeUploaded);
//        } catch (AmazonServiceException throwable) {
//            log.error(throwable.getErrorMessage());
//            throw throwable;
//        }
//        return upload;
//    }
//
//    public UploadResult await(UploadAsyncResult asyncResult) throws InterruptedException {
//        if (Upload.class.isAssignableFrom(asyncResult.getTransfer().getClass())) {
//
//        }
//        asyncResult.getManager().shutdownNow();
//        return null;
//    }

//    public Transfer uploadDirectory(String bucketName, String objectKeyName, Path filePath) {
//        TransferManager transferManager = TransferManagerBuilder.standard().build();
//        try {
//            return transferManager.uploadDirectory(bucketName,
//                    objectKeyName, filePath.toFile(), true);
//        } catch (AmazonServiceException e) {
//            System.err.println(e.getErrorMessage());
//            System.exit(1);
//        }
//        return null;
//    }
//
//    public boolean isObjectPresent(String path) {
//        AmazonS3URI uri = new AmazonS3URI(path);
//        return client.doesObjectExist(uri.getBucket(), uri.getKey());
//    }
//
//    @Builder
//    @Getter
//    public static class UploadAsyncResult {
//        TransferManager manager;
//        Transfer transfer;
//    }
}
