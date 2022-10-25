package code.shubham.blobmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateBlobResponse {
    private Integer id;
    private GetUploadSignedUrlResponse uploadSignedUrlResponse;
}
