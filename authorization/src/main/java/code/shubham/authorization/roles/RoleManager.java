package code.shubham.authorization.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleManager {

    private final RoleRepository repository;

    @Autowired
    public RoleManager(final RoleRepository repository) {
        this.repository = repository;
    }

    public Role create(final Role role) {
        return this.repository.save(role);
    }

    public Role getByRoleName(final String name) {
        return this.repository.findByName(name).orElse(null);
    }

    public Collection<Role> getAll() {
        return this.repository.findAll();
    }

    public void remove(final Integer id) {
        this.repository.deleteById(id);
    }

}
