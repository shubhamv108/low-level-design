package code.shubham.blobmodels;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class GetDownloadSignedUrlRequest {

    private Integer blobId;
    private final String contentType;
    private final Long uploadSignedUrlTimeToLiveInMilliSeconds;
    private final Map<String, String> metaData;

    public GetDownloadSignedUrlRequest(String contentType, Long uploadSignedUrlTimeToLiveInMilliSeconds, Map<String, String> metaData) {
        this.contentType = contentType;
        if (uploadSignedUrlTimeToLiveInMilliSeconds == null)
            this.uploadSignedUrlTimeToLiveInMilliSeconds = 1000000L;
        else
            this.uploadSignedUrlTimeToLiveInMilliSeconds = uploadSignedUrlTimeToLiveInMilliSeconds;
        this.metaData = metaData;
    }
}
