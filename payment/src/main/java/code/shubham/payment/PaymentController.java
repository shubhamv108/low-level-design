package code.shubham.payment;

import code.shubham.common.utils.JsonUtils;
import code.shubham.hash.SHA512;
import code.shubham.models.payment.PaymentConfirm;
import code.shubham.models.payment.PaymentInitiate;
import code.shubham.models.payment.PaymentUpdate;
import code.shubham.models.payment.TransactionStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/v1")
public class PaymentController {


    @PostMapping("/initiatePayment")
    public ResponseEntity<?> initiatePayment(
            @RequestHeader("channel") String channel,
            @RequestHeader("clientRequestTimestamp") Long clientRequestTimestamp,
            @RequestHeader("userId") String userId,
            @RequestHeader("checksum") String checksum,
            @RequestBody PaymentInitiate.Request request) throws NoSuchAlgorithmException {
        String requestChecksum = SHA512.generateAndGetHexString(JsonUtils.convert(request));
        if (!requestChecksum.equals(checksum))
            return null; // return 4xx



        return null;
    }

    @PostMapping("/updatePayment")
    public ResponseEntity<?> updatePayment(
            @RequestHeader("channel") String channel,
            @RequestHeader("clientRequestTimestamp") Long clientRequestTimestamp,
            @RequestHeader("userId") String userId,
            @RequestHeader("checksum") String checksum,
            @RequestBody PaymentUpdate.Request.Body request) throws NoSuchAlgorithmException {
        String requestChecksum = SHA512.generateAndGetHexString(JsonUtils.convert(request));
        if (!requestChecksum.equals(checksum))
            return null; // return 4xx

        return null;
    }

    @PostMapping("/confirmPayment")
    public ResponseEntity<?> confirmPayment(
            @RequestHeader("channel") String channel,
            @RequestHeader("clientRequestTimestamp") Long clientRequestTimestamp,
            @RequestHeader("userId") String userId,
            @RequestHeader("userId") String clientId,
            @RequestHeader("checksum") String checksum,
            @RequestBody PaymentConfirm.Request.Body request) throws NoSuchAlgorithmException {
        String requestChecksum = SHA512.generateAndGetHexString(JsonUtils.convert(request));
        if (!requestChecksum.equals(checksum))
            return null; // return 4xx

        return null;
    }

    @GetMapping("/paymentStatus")
    public ResponseEntity<?> transactionStatus(
            @RequestHeader("channel") String channel,
            @RequestHeader("clientRequestTimestamp") Long clientRequestTimestamp,
            @RequestHeader("userId") String userId,
            @RequestHeader("userId") String clientId,
            @RequestHeader("checksum") String checksum,
            @RequestBody TransactionStatusRequest.Body request) throws NoSuchAlgorithmException {
        String requestChecksum = SHA512.generateAndGetHexString(JsonUtils.convert(request));
        if (!requestChecksum.equals(checksum))
            return null; // return 4xx

        return null;
    }

}
