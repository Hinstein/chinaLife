package chinalife.repository;

import chinalife.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.repository
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:56
 * @Description:
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    List<Permission> findByUserId(int userId);

    Permission findByUserIdAndPerms(int userId, String perms);

    @Override
    Permission save(Permission permission);

    @Transactional
    @Modifying
    @Query(value = "delete from Permission a where a.userId =?1 and a.perms=?2 ")
    void deleteByUserIdAndPerms(int userId, String perms);

    @Transactional
    @Modifying
    @Query(value = "delete from Permission a where a.userId =?1")
    void deleteByUserId(int userId);

    Long countByPerms(String perms);
}
