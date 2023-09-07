package code.shubham.tinyurl;

import code.shubham.commons.util.StringUtils;
import code.shubham.tinyurl.strategies.IShortUrlGenerateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ShortUrlService {
    private final ShortUrlRepository repository;
    private IShortUrlGenerateStrategy shortUrlGenerateStrategy;
    private IShortUrlGenerateStrategy randomCharacterShortUrlGenerateStrategy;
    private static final int DEFAULT_SHORT_URL_LENGTH = 7;

    @Autowired
    public ShortUrlService(
            final ShortUrlRepository repository,
            @Qualifier("ShortUrlGenerateStrategy") final IShortUrlGenerateStrategy shortUrlGenerateStrategy,
            @Qualifier("RandomCharacterShortUrlGenerateStrategy") final IShortUrlGenerateStrategy randomCharacterShortUrlGenerateStrategy) {
        this.repository = repository;
        this.shortUrlGenerateStrategy = shortUrlGenerateStrategy;
        this.randomCharacterShortUrlGenerateStrategy = randomCharacterShortUrlGenerateStrategy;
    }

    public String create(final ShortURL shortURL) {
        ShortURL generatedShortURL = null;
        if (StringUtils.isNotEmpty(shortURL.getShortUrl()))
            generatedShortURL = this.repository.save(shortURL);
        else
            generatedShortURL = this.generateShortUrl(shortURL);
        return generatedShortURL.getShortUrl();
    }

    private ShortURL generateShortUrl(ShortURL shortURL) {
        String url = null;
        try {
            url = this.shortUrlGenerateStrategy.generate(
                    shortURL.getUrl(), DEFAULT_SHORT_URL_LENGTH);
        } catch (Exception exception) {
            url = this.randomCharacterShortUrlGenerateStrategy.generate(
                    shortURL.getUrl(), DEFAULT_SHORT_URL_LENGTH);
        }

        shortURL.setShortUrl(url);
        try {
            shortURL = this.repository.save(shortURL);
        } catch (DuplicateKeyException duplicateKeyException) {
            this.generateShortUrl(shortURL);
        }
        return shortURL;
    }

    public String resolve(String shortUrl, Integer userId) {
        return this.repository.findURL(shortUrl, userId);
    }
}
