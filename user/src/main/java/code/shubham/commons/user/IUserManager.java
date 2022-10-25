package code.shubham.commons.user;

import code.shubam.authorization.models.Role;

import java.util.Collection;

public interface IUserManager {

    User create(User user);
    User update(User user);
    User remove(String userName);
    User get(Long id, Collection<String> attributes);
    Collection<Role> getRoles(String userName);
    boolean addRole(String userName, Role role);
    boolean isAdmin(String userName);

}
