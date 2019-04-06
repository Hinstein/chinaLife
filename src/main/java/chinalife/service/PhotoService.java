package chinalife.service;

import chinalife.entity.Photo;
import chinalife.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.service
 * @Author: Hinstein
 * @CreateTime: 2019-04-01 20:48
 * @Description: 头像service
 */
@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    /**
     * 保存头像
     *
     * @param photo
     */
    public void save(Photo photo) {
        photoRepository.save(photo);
    }

    /**
     * 通过用户id查找头像
     *
     * @param userId
     * @return 头像信息
     */
    public Photo findByUserId(int userId) {
        return photoRepository.findByUserId(userId);
    }

    /**
     * 更新头像头像
     *
     * @param photo
     */
    public void update(Photo photo) {
        photoRepository.update(photo);
    }

    /**
     * 通过用户id删除头像
     *
     * @param id
     */
    public void deleteByUserId(int id) {
        photoRepository.deleteByUserId(id);
    }
}
