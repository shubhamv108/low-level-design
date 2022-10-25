package code.shubham.blob.stores;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Builder
@Data
public class DownloadPreSignedUrlResponse {
    private String url;
    private Map<String, List<String>> headers;
}
