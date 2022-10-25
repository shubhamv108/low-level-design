package code.shubham.commons.user;

import code.shubam.authorization.models.Role;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Component
public class UserManager {

    private static final Collection<String> ALLOWED_USER_FILTERS = new HashSet<>(Arrays.asList());

    private final UserRepository repository;

    public UserManager(final UserRepository repository) {
        this.repository = repository;
    }

    public User create(final User user) {
        return null;
    }

    public User update(final User user) {
        return null;
    }

    public User remove(final String userName) {
        return null;
    }

    public User get(Long id, Collection<String> filters) {
        if (!filters.stream().allMatch(ALLOWED_USER_FILTERS::contains))
            throw new RuntimeException();
        return this.repository.getById(id);
    }

    public Collection<Role> getRoles(final String userName) {
        return null;
    }

    public boolean addRole(final String userName, final Role role) {
        return false;
    }

    public boolean isAdmin(final String userName) {
        return false;
    }
}
