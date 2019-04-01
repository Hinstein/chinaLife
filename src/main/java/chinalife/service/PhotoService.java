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
 * @Description:
 */
@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;

    public void save(Photo photo){
        photoRepository.save(photo);
    }

    public Photo findByUserId(int userId){
        return photoRepository.findByUserId(userId);
    }

    public void update(Photo photo){
        photoRepository.update(photo);
    }
}
