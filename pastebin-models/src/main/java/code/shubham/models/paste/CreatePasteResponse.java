package code.shubham.models.paste;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CreatePasteResponse {
    private String shortUrl;
    private String uploadUrl;
    private Date uploadUrlExpiryAt;
}
