package code.shubham.trie;

import java.util.HashMap;

public class TrieNode {
    private long frequency = 0l;
    private final HashMap<Character, TrieNode> next = new HashMap<>();

    private final TopK topK = new TopK();

    private void incrementFrequency() {
        this.frequency++;
    }

    public AccessResponse access(String word) {
        if (word == null || word.isEmpty())
            return null;
        return this.access(word, word.toCharArray());
    }

    private AccessResponse access(String WORD, char[] word) {
        return this.access(WORD, word, 0);
    }

    private AccessResponse access(String WORD, char[] word, int index) {
        if (word.length == index)
            return AccessResponse.builder().wordFrequency(this.frequency).topK(this.topK.getTopK()).build();

        TrieNode next = this.next.get(word[index]);
        if (next == null)
            this.next.put(word[index], next = new TrieNode());

        next.incrementFrequency();
        AccessResponse accessResponse = next.access(WORD, word, index+1);

        this.topK.handle(WORD, accessResponse.getWordFrequency());

        return accessResponse;
    }

    public HashMap<String, Long> getTopK(String word) {
        return this.getTopK(word.toCharArray());
    }

    private HashMap<String, Long> getTopK(char[] word) {
        return this.getTopK(word, 0);
    }

    private HashMap<String, Long> getTopK(char[] word, int index) {
        if (index == word.length)
            return this.topK.getTopK();

        TrieNode next = this.next.get(word[index]);
        if (next == null)
            return null;
        return next.getTopK(word, index + 1);
    }
}
