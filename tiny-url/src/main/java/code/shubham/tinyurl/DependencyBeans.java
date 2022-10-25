package code.shubham.tinyurl;

import code.shubham.client.keygeneration.KeyGenerationClient;
import code.shubham.tinyurl.strategies.IShortUrlGenerateStrategy;
import code.shubham.tinyurl.strategies.KGSShortUrlGenerationStrategy;
import code.shubham.tinyurl.strategies.RandomCharacterShortUrlGenerateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependencyBeans {

    @Bean("ShortUrlGenerateStrategy")
    @Autowired
    public IShortUrlGenerateStrategy shortUrlGenerateStrategy(KeyGenerationClient keyGenerationClient) {
        return new KGSShortUrlGenerationStrategy(keyGenerationClient);
    }

    @Bean("RandomCharacterShortUrlGenerateStrategy")
    @Autowired
    public IShortUrlGenerateStrategy shortUrlGenerateStrategy() {
        return new RandomCharacterShortUrlGenerateStrategy();
    }

}
