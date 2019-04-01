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
 * @Description:
 */
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    Photo findByUserId(int userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Photo p  SET p.datePath= :#{#photo.datePath}," +
            "p.fileName= :#{#photo.fileName}," +
            "p.relativePath= :#{#photo.relativePath}," +
            "p.pathName= :#{#photo.pathName} " +
            "WHERE p.userId = :#{#photo.userId}")
    void update(Photo photo);
}
