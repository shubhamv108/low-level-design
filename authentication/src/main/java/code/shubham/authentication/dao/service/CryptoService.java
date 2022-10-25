package code.shubham.authentication.dao.service;

import code.shubham.CryptoUtil;
import code.shubham.authentication.constants.AuthenticationConstants;
import code.shubham.common.utils.StringUtils;
import code.shubham.hash.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CryptoService {

    private final KeyDAOService keyDAOService;

    /**
     * Time in milliseconds when captured auth value will be expired. default
     * value 30 minutes
     */
    @Value("${auth.value.expiry.milli.seconds:1800000}")
    private long timeForCaptureAuthValueExpiry;
    /**
     * flag to skip the check for captured auth value expiry time
     */
    @Value("${skip.auth.value.expiry.milli.seconds.check:-1}")
    private int skipExpiryFlag;

    private Map<String, String> authTypeEncryptionKeyMap = new HashMap<>() {
        /**
         *
         */
        private static final long serialVersionUID = -1L;

        {
            put(AuthenticationConstants.AUTH_TYPE_PASSWORD, AuthenticationConstants.DB_PASSWORD_ENCRYPTION_KEY);
        }
    };

    public CryptoService(final KeyDAOService keyDAOService) {
        this.keyDAOService = keyDAOService;
    }


    public String decryptUsingPrivateKey(String encodedAuthValue, String authType) {
        String encryptionKey = authTypeEncryptionKeyMap.get(authType);
        byte[] decodedAuthValue = HashUtil.decodeBase64(encodedAuthValue);
        byte[] privateKey = this.keyDAOService.getPrivateKey(encryptionKey);
        return new String(CryptoUtil.decryptUsingPrivateKey(privateKey, decodedAuthValue));
    }

    public String parseAndGetAuthValue(String decryptedAuthValue, String authType) {
        String authValue = "";
        long authValueCaptureTimestamp = 0;

        if (StringUtils.isNotEmpty(decryptedAuthValue)) {
            String[] parts = decryptedAuthValue.split("_");
            if (parts.length > 1) {
                authValue = parts[1].trim();
            }
            if (parts.length > 0) {
                authValueCaptureTimestamp = Long.valueOf(parts[0].trim());
            }
        }

        if (skipExpiryFlag != -1 && authValueCaptureTimestamp > 0
                && (System.currentTimeMillis() - authValueCaptureTimestamp > this.timeForCaptureAuthValueExpiry)) {
            log.error("" + authType + " capture very old (captured at : " + new Date(authValueCaptureTimestamp)
                    + " => returning blank value");
            authValue = "";
        }
        return authValue;
    }
}