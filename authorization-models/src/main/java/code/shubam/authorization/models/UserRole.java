package code.shubam.authorization.models;

import lombok.Getter;

@Getter
public class UserRole {
    private final Integer id;
    private final Integer userId;
    private final Role role;

    public UserRole(Integer id, Integer userId, Role role) {
        this.id = id;
        this.userId = userId;
        this.role = role;
    }
}