package code.shubham.blob;

import code.shubham.blobmodels.CreateBlobRequest;
import code.shubham.blobmodels.CreateBlobResponse;
import code.shubham.blobmodels.GetDownloadSignedUrlRequest;
import code.shubham.blobmodels.GetDownloadSignedUrlResponse;
import code.shubham.blobmodels.GetUploadSignedUrlRequest;
import code.shubham.blobmodels.GetUploadSignedUrlResponse;
import code.shubham.blob.stores.BlobNotFound;
import code.shubham.blob.stores.DownloadPreSignedUrlResponse;
import code.shubham.blob.stores.IBlobStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BlobService {
    private final BlobRepository repository;
    private final IBlobStore blobStore;

    @Autowired
    public BlobService(BlobRepository repository, IBlobStore blobStore) {
        this.repository = repository;
        this.blobStore = blobStore;
    }


    public CreateBlobResponse create(CreateBlobRequest request, Integer userId) {
        Blob blob = Blob.builder().
                store(request.getStore()).
                bucketName(request.getBucketName()).
                ownerService(request.getOwnerService()).
                userId(userId).
                status(BlobStatus.CREATED).
                build();
        Blob newBlob = this.repository.save(blob);

        GetUploadSignedUrlResponse getUploadSignedUrlResponse =
                this.getUploadSignedUrl(
                        request.getGetUploadSignedUrlRequest(), userId, newBlob);

        return CreateBlobResponse.builder().
                id(newBlob.getId()).
                uploadSignedUrlResponse(getUploadSignedUrlResponse).
                build();
    }

    public GetUploadSignedUrlResponse getUploadSignedUrl(
            GetUploadSignedUrlRequest request, Integer userId) {
        Blob blob = this.get(request.getBlobId());
        return this.getUploadSignedUrl(request, userId, blob);
    }

    public GetUploadSignedUrlResponse getUploadSignedUrl(
            GetUploadSignedUrlRequest request, Integer userId, Blob blob) {
        long expiryAt = System.currentTimeMillis() + request.getUploadSignedUrlTimeToLiveInMilliSeconds();
        String url = this.blobStore.getUploadPreSignedUrl(
                    blob.getBucketName(),
                    String.valueOf(blob.getId()),
                    request.getContentType(),
                    request.getUploadSignedUrlTimeToLiveInMilliSeconds(),
                    request.getMetaData());
        return GetUploadSignedUrlResponse.builder().
                url(url).
                expiryAt(new Date(expiryAt)).
                build();
    }

    public GetDownloadSignedUrlResponse getDownloadSignedUrl(
            GetDownloadSignedUrlRequest request, Integer userId) {
        Blob blob = this.get(request.getBlobId());
        this.blobStore.getDownloadPreSignedUrl(
                blob.getBucketName(), String.valueOf(blob.getId()), request.getContentType(), request.getUploadSignedUrlTimeToLiveInMilliSeconds(), request.getMetaData());
        return this.getDownloadSignedUrl(request, userId, blob);
    }

    public GetDownloadSignedUrlResponse getDownloadSignedUrl(
            GetDownloadSignedUrlRequest request, Integer userId, Blob blob) {
        long expiryAt = System.currentTimeMillis() + request.getUploadSignedUrlTimeToLiveInMilliSeconds();
        DownloadPreSignedUrlResponse response = this.blobStore.getDownloadPreSignedUrl(
                blob.getBucketName(),
                String.valueOf(blob.getId()),
                request.getContentType(),
                request.getUploadSignedUrlTimeToLiveInMilliSeconds(),
                request.getMetaData());
        return GetDownloadSignedUrlResponse.builder().
                url(response.getUrl()).
                expiryAt(new Date(expiryAt)).
                headers(response.getHeaders()).
                build();
    }

    public Blob get(Integer blobId) {
        Blob blob = this.repository.getById(blobId);
        if (blob == null)
            throw new BlobNotFound(blobId);
        return blob;
    }
}
