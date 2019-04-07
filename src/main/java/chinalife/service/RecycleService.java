package chinalife.service;

import chinalife.entity.Recycle;
import chinalife.repository.RecycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.service
 * @Author: Hinstein
 * @CreateTime: 2019-04-07 12:52
 * @Description:
 */
@Service
public class RecycleService {

    @Autowired
    RecycleRepository recycleRepository;

    public void deleteById(int id){
        recycleRepository.deleteById(id);
    }

    public void save(Recycle recycle){
        recycleRepository.save(recycle);
    }

    public Page<Recycle> findAllByClerkId(int clerkId,int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return recycleRepository.findAllByClerkId(clerkId,pageable);
    }

    public Recycle findById(int id){
        return recycleRepository.findById(id);
    }
}
