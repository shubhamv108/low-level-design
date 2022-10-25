package code.shubham.authorization.userroles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Collection<UserRole> findAllByUserId(Integer userId);
    Collection<UserRole> findAllByUserIdAndRoleId(Integer userId, Integer roleId);

    @Query("SELECT ur FROM UserRole ur JOIN code.shubham.authorization.roles.Role r ON ur.role.id = r.id WHERE ur.userId = :userId AND ur.role.name = :roleName")
    Collection<UserRole> findAllByUserIdAndRoleName(@Param("userId") Integer userId,
                                                    @Param("roleName") String roleName);
}
