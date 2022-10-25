package code.shubham.authorization.userroles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserRoleManager {

    private final UserRoleRepository repository;

    @Autowired
    public UserRoleManager(final UserRoleRepository repository) {
        this.repository = repository;
    }

    public UserRole create(final UserRole userRole) {
        return this.repository.save(userRole);
    }

    public void remove(final Integer id) {
        this.repository.deleteById(id);
    }

    public Collection<UserRole> getByUserId(final Integer userId) {
        return this.repository.findAllByUserId(userId);
    }

    public Collection<UserRole> getByUserIdAndRoleId(final Integer userId, final Integer roleId) {
        return this.repository.findAllByUserIdAndRoleId(userId, roleId);
    }

    public Collection<UserRole> getByUserIdAndRoleName(final Integer userId, final String roleName) {
        return this.repository.findAllByUserIdAndRoleName(userId, roleName);
    }
}
