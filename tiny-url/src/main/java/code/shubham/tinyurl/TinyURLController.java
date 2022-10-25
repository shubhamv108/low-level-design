package code.shubham.tinyurl;

import code.shubham.commons.util.ResponseUtils;
import code.shubham.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("")
public class TinyURLController {

    private final ShortUrlService service;

    @Autowired
    public TinyURLController(final ShortUrlService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader("userId") Integer userId,
                                    @RequestBody ShortURL shortURL) {
        if (StringUtils.isEmpty(shortURL.getUrl()))
            return ResponseUtils.getErrorResponseEntity(400, new HashMap<>() {{
                put("url", "url must not be null or empty");
            }});
        if (userId != null)
            shortURL.setUserId(userId);
        String shortUrl = this.service.create(shortURL);
        return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED.value(), new HashMap<>() {{
            put("url", shortURL.getShortUrl());
        }});
    }

    @GetMapping("/{tinyURL}")
    public ResponseEntity<?> redirect(@RequestHeader("userId") Integer userId,
                                      @PathVariable("tinyURL") String tinyURL) {
        if (StringUtils.isEmpty(tinyURL))
            return ResponseUtils.getErrorResponseEntity(400, new HashMap<>() {{
                put("tinyURL", "tinyURL must not be null or empty");
            }});
        String url = this.service.resolve(tinyURL, userId);
        if (StringUtils.isEmpty(url))
            return ResponseUtils.getErrorResponseEntity(400, new HashMap<>() {{
                put("tinyURL", "tinyURL not found");
            }});
        return ResponseUtils.getResponseEntity(302,
                null,
                null,
                "Location", url);
    }

}
