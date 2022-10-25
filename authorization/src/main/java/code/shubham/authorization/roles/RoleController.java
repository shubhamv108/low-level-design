package code.shubham.authorization.roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleManager manager;

    @Autowired
    public RoleController(final RoleManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public Role create(
            @RequestHeader("userId") final Integer userId,
            @RequestBody final Role role) {
        return this.manager.create(role);
    }

    @GetMapping
    public Collection<Role> get() {
        return this.manager.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.manager.remove(id);
    }
}
