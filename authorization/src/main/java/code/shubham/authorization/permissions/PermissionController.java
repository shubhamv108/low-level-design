package code.shubham.authorization.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    public final PermissionManager manager;

    @Autowired
    public PermissionController(final PermissionManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public Permission create(@RequestHeader("userId") final Integer userId,
                             @RequestBody final Permission permission) {
        return this.manager.create(permission);
    }

    @DeleteMapping("/{id}")
    public void remove(@RequestHeader("userId") final Integer userId,
                       @PathVariable("id") final Integer id) {
        this.manager.remove(id);
    }

    @GetMapping("/{id}")
    public Permission get(@RequestHeader("userId") final Integer userId,
                    @RequestBody final Integer id) {
        return this.manager.get(id);
    }

}
