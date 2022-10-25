package code.shubham.tinyurl.strategies;

import code.shubham.client.keygeneration.KeyGenerationClient;
import org.springframework.beans.factory.annotation.Autowired;

public class KGSShortUrlGenerationStrategy implements IShortUrlGenerateStrategy {

    private final KeyGenerationClient keyGenerationClient;

    @Autowired
    public KGSShortUrlGenerationStrategy(final KeyGenerationClient keyGenerationClient) {
        this.keyGenerationClient = keyGenerationClient;
    }

    @Override
    public String generate(String originalUrl, Integer shortUrlLength) {
        return this.keyGenerationClient.poll();
    }
}
