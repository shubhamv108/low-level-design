package code.shubham.client.blob;

import code.shubham.blobmodels.CreateBlobRequest;
import code.shubham.blobmodels.CreateBlobResponse;
import code.shubham.blobmodels.GetDownloadSignedUrlRequest;
import code.shubham.blobmodels.GetDownloadSignedUrlResponse;
import code.shubham.blobmodels.GetUploadSignedUrlRequest;
import code.shubham.blobmodels.GetUploadSignedUrlResponse;
import code.shubham.commonclient.HttpClientUtils;
import code.shubham.commonclient.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
public class BlobClient {

    @Value("${host.blob:127.0.0.1}")
    private String host = "127.0.0.1";

    @Value("${port.authorization: #{8089}}")
    private Integer port = 8089;
    private final String baseUrl;
    private static final String CONTEXT_PATH = "/blob";

    private final HttpClientUtils httpClientUtils;

    private static final String SERVICE_NAME = "Blob";

    @Autowired
    public BlobClient(final HttpClientUtils httpClientUtils) {
        this.httpClientUtils = httpClientUtils;
        this.baseUrl = "http://" + this.host + ":" + this.port + CONTEXT_PATH;
    }

    public CreateBlobResponse create(CreateBlobRequest createBlobRequest, Integer userId) {
        HttpRequest request = this.httpClientUtils.createRequest(
                "POST",
                this.baseUrl + "/",
                null,
                JsonUtils.seriealize(createBlobRequest),
                "userId", String.valueOf(userId));
        return this.httpClientUtils.sendRequest(request, CreateBlobResponse.class, SERVICE_NAME);
    }

    public GetUploadSignedUrlResponse getUploadPreSignedUrl(
            GetUploadSignedUrlRequest getUploadSignedUrlRequest,
            Integer userId) {
        HttpRequest request = this.httpClientUtils.createRequest(
                "GET",
                this.baseUrl + "/uploadPreSignedUrl",
                null,
                JsonUtils.seriealize(getUploadSignedUrlRequest),
                "userId", String.valueOf(userId));
        return this.httpClientUtils.sendRequest(request, GetUploadSignedUrlResponse.class, SERVICE_NAME);
    }

    public GetDownloadSignedUrlResponse getById(
            GetDownloadSignedUrlRequest getDownloadSignedUrlRequest,
            Integer userId) {
        HttpRequest request = this.httpClientUtils.createRequest(
                "GET",
                this.baseUrl + "/",
                null,
                JsonUtils.seriealize(getDownloadSignedUrlRequest),
                "userId", String.valueOf(userId));
        return this.httpClientUtils.sendRequest(request, GetDownloadSignedUrlResponse.class, SERVICE_NAME);
    }
}
