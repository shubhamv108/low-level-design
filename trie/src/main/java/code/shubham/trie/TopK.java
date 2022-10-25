package code.shubham.trie;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;

public class TopK {

    private final TreeMap<Long, LinkedHashSet<String>> frequenciesTopK = new TreeMap<>();

    private final HashMap<String, Long> topKWordFrequency = new HashMap<>();

    private static final int TOP_K_COUNT = 20;

    public void handle(final String word, final long wordFrequency) {
        Long existingWordFrequency = this.topKWordFrequency.get(word);
        if (existingWordFrequency != null)
            this.frequenciesTopK.get(existingWordFrequency).remove(word);

        if (wordFrequency > this.frequenciesTopK.firstKey())
            this.evict();

        if (this.topKWordFrequency.size() < TOP_K_COUNT) {
            LinkedHashSet<String> words = this.frequenciesTopK.get(wordFrequency);
            if (words == null)
                this.frequenciesTopK.put(wordFrequency, words = new LinkedHashSet<>());
            words.add(word);
            this.topKWordFrequency.put(word, wordFrequency);
        }
    }

    private void evict() {
        if (this.topKWordFrequency.size() < TOP_K_COUNT)
            return;
        String toEvict = this.frequenciesTopK.firstEntry().getValue().iterator().next();
        this.frequenciesTopK.firstEntry().getValue().remove(toEvict);
    }

    public HashMap<String, Long> getTopK() {
        return new HashMap<>(this.topKWordFrequency);
    }
}
