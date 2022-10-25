package code.shubham.sequencegenerator;

import code.shubham.commons.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SequenceController {

    private final SequenceService sequenceService;

    @Autowired
    public SequenceController(SequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    @PostMapping("accept")
    public ResponseEntity<?> accept(@RequestHeader(value = "userId") Integer userId) {
        AcceptedUser acceptedUser;
        try {
            acceptedUser = this.sequenceService.accept(userId);
        } catch (DuplicateKeyException duplicateKeyException) {
            return ResponseUtils.getResponseEntity(HttpStatus.ALREADY_REPORTED);
        }
        return ResponseUtils.getDataResponseEntity(HttpStatus.ACCEPTED, acceptedUser);
    }

    @PutMapping("accept")
    public ResponseEntity<?> acceptToFirstEmpty(@RequestHeader(value = "userId") Integer userId) {
        try {
            var updatedRows = this.sequenceService.acceptToFirstEmpty(userId);
            if (updatedRows == 0)
                return ResponseUtils.getResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            return ResponseUtils.getResponseEntity(HttpStatus.ACCEPTED);
        } catch (DuplicateKeyException duplicateKeyException) {
            return ResponseUtils.getResponseEntity(HttpStatus.ACCEPTED);
        }
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestHeader(value = "userId", required = false) Integer userId) {
        return ResponseEntity.
                status(HttpStatus.ACCEPTED).
                body(this.sequenceService.create(userId));
    }
}
