package code.shubham.blobmodels;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class GetUploadSignedUrlResponse {
    private String url;
    private Date expiryAt;
}
