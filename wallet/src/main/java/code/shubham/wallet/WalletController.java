package code.shubham.wallet;

import code.shubham.models.wallet.WalletBalanceTransfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class WalletController {

    @PostMapping("/balanceTransfer")
    public ResponseEntity<?> balanceTransfer(
            @RequestHeader("checksum") String checksum,
            @RequestBody WalletBalanceTransfer.Request request) {
        return null;
    }

}
