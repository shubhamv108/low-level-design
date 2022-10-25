package code.shubham.trie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;

@Builder
@Getter
@AllArgsConstructor
public class AccessResponse {
    private final long wordFrequency;
    private final HashMap<String, Long> topK;
}
