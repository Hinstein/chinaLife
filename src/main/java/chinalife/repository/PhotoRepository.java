package chinalife.repository;

import chinalife.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.repository
 * @Author: Hinstein
 * @CreateTime: 2019-04-01 20:47
 * @Description: 头像dao层
 */
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    /**
     * 通过用户id查找头像
     *
     * @param userId
     * @return 头像实体
     */
    Photo findByUserId(int userId);

    /**
     * 更新头像信息
     *
     * @param photo
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "UPDATE Photo p  SET p.datePath= :#{#photo.datePath}," +
            "p.fileName= :#{#photo.fileName}," +
            "p.relativePath= :#{#photo.relativePath}," +
            "p.pathName= :#{#photo.pathName} " +
            "WHERE p.userId = :#{#photo.userId}")
    void update(Photo photo);

    /**
     * 通过用户id删除头像
     *
     * @param userId
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "delete from Photo a where a.userId =?1")
    void deleteByUserId(int userId);
}
