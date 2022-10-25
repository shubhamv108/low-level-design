package code.shubham.authorization.userroles;

import code.shubham.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/")
public class UserRoleController {

    private final UserRoleManager manager;

    @Autowired
    public UserRoleController(final UserRoleManager manager) {
        this.manager = manager;
    }

    @PostMapping
    public UserRole create(
            @RequestHeader("userId") Integer userId,
            @RequestBody UserRole userRole) {
        if (userId != userRole.getUserId())
            throw new UserIdNotFoundException();
        return this.manager.create(userRole);
    }

    @GetMapping
    public Collection<UserRole> get(@RequestHeader("userId") Integer userId,
                                    @RequestParam(value = "roleId", required = false)  Integer roleId,
                                    @RequestParam(value = "roleName", required = false) String roleName) {
        if (roleId == null && StringUtils.isEmpty(roleName))
            return this.manager.getByUserId(userId);

        Collection<UserRole> response = new ArrayList<>();
        if (roleId != null)
            response.addAll(this.manager.getByUserIdAndRoleId(userId, roleId));
        if (StringUtils.isNotEmpty(roleName))
            response.addAll(this.manager.getByUserIdAndRoleName(userId, roleName));
        return response;
    }
}
