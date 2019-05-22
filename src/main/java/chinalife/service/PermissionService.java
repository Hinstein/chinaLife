package chinalife.service;

import chinalife.entity.Permission;
import chinalife.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.service
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:57
 * @Description: 权限service
 */
@Service
public class PermissionService {

    @Autowired
    PermissionRepository permsRepository;

    /**
     * 通过用户id查找权限
     *
     * @param userId
     * @return 权限
     */
    public List<Permission> findByUserId(int userId) {
        return permsRepository.findByUserId(userId);
    }

    /**
     * 通过用户id和权限名称查找权限
     *
     * @param userId
     * @param perms
     * @return 权限
     */
    public Permission findByUserIdAndPerms(int userId, String perms) {
        return permsRepository.findByUserIdAndPerms(userId, perms);
    }

    /**
     * 保存权限
     *
     * @param permission
     */
    public void save(Permission permission) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        permission.setTime(df.format(new Date()));
        permsRepository.save(permission);
    }

    /**
     * 通过用户id的权限名称删除权限
     *
     * @param userId
     * @param perms
     */
    public void deleteByUserIdAndPerms(int userId, String perms) {
        permsRepository.deleteByUserIdAndPerms(userId, perms);
    }

    /**
     * 通过用户id删除权限
     *
     * @param userId
     */
    public void deleteByUserId(int userId) {
        permsRepository.deleteByUserId(userId);
    }

    /**
     * 删除的权限数量
     *
     * @return 删除的权限数量
     */
    public long createCounts() {
        return permsRepository.countByPerms("create")-1;
    }

    /**
     * 更新的权限数量
     *
     * @return 更新的权限数量
     */
    public long updateCounts() {
        return permsRepository.countByPerms("update")-1;
    }

    /**
     * 删除的权限数量
     *
     * @return 删除的权限数量
     */
    public long deleteCounts() {
        return permsRepository.countByPerms("delete")-1;
    }

    /**
     * 查看的权限数量
     *
     * @return 查看的权限数量
     */
    public long retrieveCounts() {
        return permsRepository.countByPerms("retrieve")-1;
    }

}
