package code.shubham.authentication.dao.entities;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

@Slf4j
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "keys")
public class Key extends BaseAbstractAuditableEntity {
    @Column(name = "alias")
    private String alias;

    @Lob
    @Column(name = "p12_key")
    private byte[] key;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "purpose", unique = true)
    private String purpose;

    public PrivateKey getPrivateKey() {
        ByteArrayInputStream inputStream = null;
        PrivateKey privateKey = null;
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
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
            KeyStore ks = KeyStore.getInstance("PKCS12");
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
}