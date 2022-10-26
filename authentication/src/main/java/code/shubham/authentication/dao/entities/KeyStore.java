package code.shubham.authentication.dao.entities;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

@Slf4j
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "key_stores")
public class KeyStore extends BaseAbstractAuditableEntity {
    @Column(name = "alias")
    private String alias;

    @Lob
    @Column(name = "p12_key")
    private byte[] key;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private String type;

    @Column(name = "purpose", unique = true, nullable = false)
    private String purpose;

    public PrivateKey getPrivateKey() {
        ByteArrayInputStream inputStream = null;
        PrivateKey privateKey = null;
        try {
            java.security.KeyStore ks = java.security.KeyStore.getInstance("PKCS12");
            inputStream = new ByteArrayInputStream(this.key);
            ks.load(inputStream, this.password.toCharArray());
            privateKey = (PrivateKey) ks.getKey(this.alias, this.password.toCharArray());
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        return privateKey;
    }

    public PublicKey getPublicKey() {
        ByteArrayInputStream inputStream = null;
        PublicKey publicKey = null;
        try {
            java.security.KeyStore ks = java.security.KeyStore.getInstance("PKCS12");
            inputStream = new ByteArrayInputStream(this.key);
            ks.load(inputStream, this.password.toCharArray());
            publicKey = ks.getCertificate(this.alias).getPublicKey();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        return publicKey;
    }

    public SecretKey getSecretKey() {
        ByteArrayInputStream inputStream = null;
        SecretKey secretKey = null;
        try {
            java.security.KeyStore ks = java.security.KeyStore.getInstance("PKCS12");
            inputStream = new ByteArrayInputStream(this.key);
            ks.load(inputStream, this.password.toCharArray());
            secretKey = (SecretKey) ks.getKey(this.alias, this.password.toCharArray());
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        return secretKey;
    }
}