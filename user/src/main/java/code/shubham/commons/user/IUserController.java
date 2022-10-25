package code.shubham.commons.user;

import java.util.Collection;

public interface IUserController {
    User create(User user);
    User update(User user);

    User get(Long userId, Collection<String> attributes);
    User remove(String userName);
    boolean isAdmin(String userName);
}
