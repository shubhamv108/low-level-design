package code.shubham.blobmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateBlobRequest {
    private final String store;
    private final String bucketName;
    private final String checksum;
    private final String ownerService;

    private final GetUploadSignedUrlRequest getUploadSignedUrlRequest;

    public CreateBlobRequest(
            String store,
            String bucketName,
            String checksum,
            String ownerService,
            GetUploadSignedUrlRequest getUploadSignedUrlRequest) {
        this.store = store;
        this.bucketName = bucketName;
        this.checksum = checksum;
        this.ownerService = ownerService;
        this.getUploadSignedUrlRequest = getUploadSignedUrlRequest;
    }
}
