package code.shubham.blobmodels;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class GetDownloadSignedUrlResponse {
    private String url;
    private Date expiryAt;
    private Map<String, List<String>> headers;
}
