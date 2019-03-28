package chinalife.service;

import chinalife.entity.Insurance;
import chinalife.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.service
 * @Author: Hinstein
 * @CreateTime: 2019-03-27 19:57
 * @Description:
 */
@Service
public class InsuranceService {
    @Autowired
    InsuranceRepository insuranceRepository;

    public void save(Insurance insurance){
        insuranceRepository.save(insurance);
    }

    public Page<Insurance> findAll(int pageNo, int pageSize){
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findAll(pageable);
    }

    public Page<Insurance> findAllByClerkId(int clerkId,int pageNo, int pageSize){
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findAllByClerkId(clerkId,pageable);
    }
}
