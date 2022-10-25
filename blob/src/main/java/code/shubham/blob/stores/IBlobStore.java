package code.shubham.blob.stores;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface IBlobStore {


    boolean createBucketIfNotExists(String bucketName);

    String getUploadPreSignedUrl(String bucketName, String objectName, String contentType, long timeToLiveInMilliseconds, Map<String, String> metadata);

    DownloadPreSignedUrlResponse getDownloadPreSignedUrl(String bucketName, String objectKeyName, String contentType, long timeToLiveInMilliseconds, Map<String, String> metadata);

    String uploadFile(String bucketName, String objectKeyName, String fileName, String contentType, Map<String, String> userMetadata);

    Path downloadFile(String bucketName, String objectKeyName, String filePath) throws IOException;

    void copy(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey);
}
