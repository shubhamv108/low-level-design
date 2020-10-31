package lowleveldesign.facebook.api.core.entities;


import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Table("persons")
public class Person {

    @PrimaryKeyColumn(name = "person_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @Column("avatar_url")
    private String avatarUrl;

    @Column("thumbnail_url")
    private String thumbnailUrl;

    @Column("first_name")
    private String firstName;

    @Column("middle_name")
    private String middleName;

    @Column("last_name")
    private String lastName;

    @Column("mobile_number")
    private String mobileNumber;

    @Column("is_mobile_number_verified")
    private boolean isMobileNumberVerified;

    @Column("email_id")
    private String email;

    @Column("is_email_verified")
    private boolean isEmailVerified;

    @Column("gender")
    private String gender;

    @Column("native_language")
    private String native_languages;

    @Column("languages")
    private Set<String> languages;

    private String studyingAt;

    private Set<String> studiedAt;

    @Column("working_at")
    private String workingAt;

    @Column("workedAt")
    private Set<String> workedAt;

    @Column("profile_summary")
    private String profileSummary;

    @Column("user_id")
    private String userId;

    @Column("user_name")
    private String userName;

    @Column("roles")
    private Set<String> roles;

    @Column("password")
    private String password;

    @Column("temporary_password")
    private String temporaryPassword;

    @Column("is_temporary_password_changed")
    private boolean isTemporaryPasswordChanged;

    @Column("is_deleted")
    private boolean isDeleted;

    @Column("is_active")
    private boolean isActive;

    @Column("last_active_location")
    private String lastActiveLocation;

    @Column("last_login_location")
    private String lastLoginLocation;

    @Column("last_login_at")
    private Date lastLoginAt;

    @Column("created_at")
    private Date createdAt;

    @Column("created_by")
    private String createdBy;

    @Column("modified_at")
    private Date modifiedAt;

    @Column("modified_by")
    private String modifiedBy;

}
