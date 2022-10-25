package code.shubham.escrow;

import code.shubham.models.escrow.CreateEscrow;
import code.shubham.models.escrow.SignAgreement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class EscrowController {

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody CreateEscrow.Request.Body request) {
        return null;
    }

    @PostMapping("/inviteReceiver/{agreementId}")
    public ResponseEntity<?> inviteReceiver(
            @PathVariable("agreementId") Integer agreementId) {
        return null;
    }

    @PostMapping("/signAgreement/{agreementId}")
    public ResponseEntity<?> signAgreement(
            @RequestBody SignAgreement.Request.Body request) {
        return null;
    }

    @PostMapping("/fund/{agreementId}")
    public ResponseEntity<?> fund(
            @RequestBody SignAgreement.Request.Body request) {
        // generate and save payment order id and initiate transaction
        // initiate transaction
        return null;
    }

    @PostMapping("/conditionFulfiled/{agreementId}")
    public ResponseEntity<?> conditionFulfiled(
            @RequestParam("agreementId") Integer agreementId) {
        // generate and save payment order id and initiate transaction
        // initiate transaction
        return null;
    }
}
