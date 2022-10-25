package code.shubham.system.offer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RuleController {

    @PostMapping("/create")
    public ResponseEntity<?> create() {
        return null;
    }
}
