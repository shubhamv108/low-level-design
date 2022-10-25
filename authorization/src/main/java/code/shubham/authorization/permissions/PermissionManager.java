package code.shubham.authorization.permissions;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class PermissionManager {

    private final PermissionRepository repository;

    public PermissionManager(final PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission create(@RequestBody final Permission permission) {
        return this.repository.save(permission);
    }

    public void remove(@RequestBody final Integer id) {
        this.repository.deleteById(id);
    }

    public Permission get(@RequestBody final Integer id) {
        return this.repository.getById(id);
    }

}
