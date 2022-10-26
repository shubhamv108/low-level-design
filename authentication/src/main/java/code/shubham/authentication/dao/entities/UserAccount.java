package code.shubham.authentication.dao.entities;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_accounts")
public class UserAccount extends BaseAbstractAuditableEntity {

    private static final long serialVersionUUID = 1L;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false, length = 256)
    private String password;

    @Builder.Default
    @JsonIgnore
    @Column(nullable = false)
    private boolean isExpired = false;

    @Builder.Default
    @JsonIgnore
    @Column(nullable = false)
    private boolean isLocked = false;

    @Builder.Default
    @JsonIgnore
    @Column(nullable = false)
    private boolean isCredentialsExpired = true;

    @Builder.Default
    @JsonIgnore
    @Column(nullable = false)
    private boolean isEnabled = false;

    @Builder.Default
    @JsonIgnore
    private boolean isDeleted = false;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private Date expiresOn;

    private transient Collection<? extends GrantedAuthority> authorities;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    public boolean isCredentialsNonExpired() {
        return !this.isCredentialsExpired;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}
