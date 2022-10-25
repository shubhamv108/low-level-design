package code.shubham.trie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/")
public class TrieController {

    @Value("${query.max-length:50}")
    private Integer queryMaxLength = 50;

    private final TrieNode rootTrieNode;

    @Autowired
    public TrieController(@Qualifier("RootTrieNode") TrieNode rootTrieNode) {
        this.rootTrieNode = rootTrieNode;
    }

    @GetMapping
    public ResponseEntity<?> query(@RequestParam("query") String query) {
        query = query.substring(this.queryMaxLength);
        HashMap<String, Long> topK = this.rootTrieNode.getTopK(query);
        return ResponseEntity.ok(topK);
    }

    @GetMapping("access")
    public ResponseEntity<?> access(@RequestParam("query") String query) {
        query = query.substring(this.queryMaxLength);
        AccessResponse accessResponse = this.rootTrieNode.access(query);
        return ResponseEntity.ok(accessResponse.getTopK());
    }

}
