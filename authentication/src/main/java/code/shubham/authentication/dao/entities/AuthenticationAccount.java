package code.shubham.authentication.dao.entities;

import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class AuthenticationAccount extends BaseAbstractAuditableEntity implements UserDetails, Serializable {

    private static final long serialVersionUUID = 1L;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private Integer userId;

    @JsonIgnore
    @Column(nullable = false, length = 256)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isExpired = false;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isLocked = false;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isCredentialsExpired = true;

    @JsonIgnore
    @Column(nullable = false)
    private boolean isEnabled = false;

    @JsonIgnore
    private boolean isDeleted;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private Date expiresOn;

    private transient Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
