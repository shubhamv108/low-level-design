package code.shubham.trie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeanConfiguration {

    @Bean("RootTrieNode")
    public TrieNode trieNode() {
        return new TrieNode();
    }
}
