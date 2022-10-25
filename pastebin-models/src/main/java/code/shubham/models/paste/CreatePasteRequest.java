package code.shubham.models.paste;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePasteRequest {
    String checksum;
    Long timeToLiveInMilliSeconds = 10000000L;
}
