package code.shubham.authentication.utils;

import code.shubham.authentication.configuration.SecurityProperties;
import code.shubham.authentication.enums.SessionTimeoutType;
import code.shubham.common.utils.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class JWTUtils {

    private SecurityProperties securityProperties;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public JWTUtils(final SecurityProperties securityProperties,
                    final PasswordEncoder passwordEncoder) {
        this.securityProperties = securityProperties;
        this.passwordEncoder = passwordEncoder;
    }

    private static String _UT_ID = "_UT#";
    private static String _UT_ID_Key = "UKID";
    private String jti_Secret_key = "Mwl#nztky@713";


    public String generateAccessToken(String authenticationUser, String role, String utid, String jti,
                                      Date expiration, String secret, String remoteAddress) {

        Map<String, Object> tokenHeaderClaim = new HashMap<>();
        tokenHeaderClaim.put(JWTUtils._UT_ID_Key, utid);
//        tokenHeaderClaim.put("role", role);
        if (StringUtils.isNotEmpty(remoteAddress)) {
            tokenHeaderClaim.put("remoteAddress", remoteAddress);
        }

        return Jwts.builder()
                .setHeaderParam("alg", SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .addClaims(tokenHeaderClaim)
                .setSubject(authenticationUser)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .setId(jti)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimsFromAccessToken(String token, String secret) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public boolean validateExpirationDate(Claims claims) {
        Date expDate = claims.getExpiration();
        return expDate.after(new Date());
    }

    public int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt((this.securityProperties.getSecret().size()-1 - 0) + 1) + 0;
    }

    public String prepareUTID(int key) {
        return TextCodec.BASE64.encode(JWTUtils._UT_ID + key);
    }

    public String getUTID(String data) {
        String decodeData = TextCodec.BASE64.decodeToString(data);
        String[] utid = decodeData.split("#");
        return utid.length > 0 ? utid[1] : null;
    }

    public String generateJti() {
        return this.passwordEncoder.encode(jti_Secret_key);
    }

    public boolean validateJti(String jti) {
        return this.passwordEncoder.matches(jti, jti_Secret_key);
    }

}