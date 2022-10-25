package code.shubham.commons.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import code.shubham.commons.entities.base.BaseAbstractAuditableEntity;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        }),
        @UniqueConstraint(columnNames = {
                "mobileNumber"
        })
})
public class User extends BaseAbstractAuditableEntity {

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(unique = true, length = 15)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String password;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    @Column(nullable = false, updatable = false, length = 40)
    private String email;

    @Size(max = 10)
    @Column(updatable = true, length = 10)
    private String mobileNumber;

    @NaturalId
    @NotBlank
    @Size(max = 12)
    @OneToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    public void resetPassword(final String newPassword) {
        this.password = newPassword;
    }

}