package chinalife.service;

import chinalife.entity.Permission;
import chinalife.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.service
 * @Author: Hinstein
 * @CreateTime: 2019-03-18 08:57
 * @Description:
 */
@Service
public class PermissionService {

    @Autowired
    PermissionRepository permsRepository;

    public List<Permission> findByUserId(int userId){
        return permsRepository.findByUserId(userId);
    }

    public Permission findByUserIdAndPerms(int userId,String perms){
        return permsRepository.findByUserIdAndPerms(userId,perms);
    }

    public void save(Permission permission){
        permsRepository.save(permission);
    }

    public void deleteByUserIdAndPerms(int userId,String perms){
         permsRepository.deleteByUserIdAndPerms(userId,perms);
    }

    public void deleteByUserId(int userId){
        permsRepository.deleteByUserId(userId);
    }
}
