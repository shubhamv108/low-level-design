package code.shubham.blob;

import code.shubham.blobmodels.CreateBlobRequest;
import code.shubham.blobmodels.CreateBlobResponse;
import code.shubham.blobmodels.GetDownloadSignedUrlRequest;
import code.shubham.blobmodels.GetUploadSignedUrlRequest;
import code.shubham.blob.stores.IBlobStore;
import code.shubham.commons.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BlobController {

    private final BlobService service;
    private final IBlobStore blobStore;

    @Autowired
    public BlobController(BlobService service, IBlobStore blobStore) {
        this.service = service;
        this.blobStore = blobStore;
    }

    @PostMapping
    public ResponseEntity<?> createBlobAndGetUploadSignedUrl(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestBody CreateBlobRequest request) {
        CreateBlobResponse response = this.service.create(request, userId);
        return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED.value(), response);
    }

    @GetMapping("uploadPreSignedUrl")
    public ResponseEntity<?> getUploadPreSignedUrl(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestBody GetUploadSignedUrlRequest request) {
       var response = this.service.getUploadSignedUrl(request, userId);
       return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED.value(), response);
    }

    @GetMapping
    public ResponseEntity<?> get(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestBody GetDownloadSignedUrlRequest request) {
        var response = this.service.getDownloadSignedUrl(request, userId);
        return ResponseUtils.getResponseEntity(
                HttpStatus.TEMPORARY_REDIRECT.value(),
                response,
                response.getHeaders(),
                "Location", response.getUrl());
    }

}
