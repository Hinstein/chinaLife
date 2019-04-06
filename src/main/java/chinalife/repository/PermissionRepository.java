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
 * @Description: 权限dao层
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    /**
     * 通过用户id查找权限
     *
     * @param userId
     * @return 权限信息
     */
    List<Permission> findByUserId(int userId);

    /**
     * 通过用户id和权限名称查找权限
     *
     * @param userId
     * @param perms
     * @return 权限信息
     */
    Permission findByUserIdAndPerms(int userId, String perms);

    /**
     * 通过用户id和权限名称删除全新
     *
     * @param userId
     * @param perms
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "delete from Permission a where a.userId =?1 and a.perms=?2 ")
    void deleteByUserIdAndPerms(int userId, String perms);

    /**
     * 通过用户id删除权限
     *
     * @param userId
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "delete from Permission a where a.userId =?1")
    void deleteByUserId(int userId);

    /**
     * 查询权限总数
     *
     * @param perms
     * @return 权限数量
     */
    Long countByPerms(String perms);
}
