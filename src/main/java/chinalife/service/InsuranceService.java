package chinalife.service;

import chinalife.entity.Insurance;
import chinalife.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public void save(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    public Page<Insurance> findAll(int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findAll(pageable);
    }

    public Page<Insurance> findAllByClerkId(int clerkId, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findAllByClerkId(clerkId, pageable);
    }

    public void deleteById(int id) {
        insuranceRepository.deleteById(id);
    }

    public Insurance findById(int id) {
        return insuranceRepository.findById(id);
    }

    public void update(Insurance insurance) {
        insuranceRepository.updateInsruance(insurance);
    }

    public int baodan0() {
        return insuranceRepository.countByPolName(0);
    }

    public int baodan1() {
        return insuranceRepository.countByPolName(1);
    }

    public int baodan2() {
        return insuranceRepository.countByPolName(2);
    }

    public int baodan3() {
        return insuranceRepository.countByPolName(3);
    }

    public int baodan0(int id) {
        return insuranceRepository.countByPolNameAndClerkId(0,id);
    }

    public int baodan1(int id) {
        return insuranceRepository.countByPolNameAndClerkId(1,id);
    }
    public int baodan2(int id) {
        return insuranceRepository.countByPolNameAndClerkId(2,id);
    }
    public int baodan3(int id) {
        return insuranceRepository.countByPolNameAndClerkId(3,id);
    }

    public Long numbers()
    {
        return insuranceRepository.count();
    }

    public Long numbers(int id)
    {
        return insuranceRepository.countByClerkId(id);
    }

    @Transactional
    public Page<Insurance> findByContent(String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findBycontent(content, pageable);
    }



    @Transactional
    public Page<Insurance> findByClerkIdAndContent(String content,int clerkId, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findByClerkIdAndContent(content,clerkId, pageable);
    }
}
