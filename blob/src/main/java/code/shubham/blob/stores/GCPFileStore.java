package code.shubham.blob.stores;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Component("GCPFileStore")
public class GCPFileStore implements IBlobStore {
    @Override
    public boolean createBucketIfNotExists(String bucketName) {
        return false;
    }

    @Override
    public String getUploadPreSignedUrl(String bucketName, String objectName, String contentType, long timeToLiveInMilliseconds, Map<String, String> metadata) {
        return null;
    }

    @Override
    public DownloadPreSignedUrlResponse getDownloadPreSignedUrl(String bucketName, String objectKeyName, String contentType, long timeToLiveInMilliseconds, Map<String, String> metadata) {
        return null;
    }

    @Override
    public String uploadFile(String bucketName, String objectKeyName, String fileName, String contentType, Map<String, String> userMetadata) {
        return null;
    }

    @Override
    public Path downloadFile(String bucketName, String objectKeyName, String filePath) throws IOException {
        return null;
    }

    @Override
    public void copy(String sourceBucket, String sourceKey, String destinationBucket, String destinationKey) {

    }
}
