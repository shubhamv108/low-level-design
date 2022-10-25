package code.shubham.commons.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {

    private final UserManager manager;

    public UserController(final UserManager manager) {
        this.manager = manager;
    }

    @PutMapping
    public User create(final User user) {
        return this.manager.create(user);
    }

    @PatchMapping
    public User update(final User user) {
        return this.manager.update(user);
    }

    @DeleteMapping
    public User remove(final String userName) {
        return this.manager.remove(userName);
    }

    @GetMapping("/{userName}")
    public User get(@PathVariable("userId") final Long userId,
                    @RequestParam("query") Collection<String> attributes) {
        return this.manager.get(userId, attributes);
    }

}
