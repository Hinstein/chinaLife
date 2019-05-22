package chinalife.service;

import chinalife.entity.Insurance;
import chinalife.entity.User;
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
 * @Description: 保单service层
 */
@Service
public class InsuranceService {
    @Autowired
    InsuranceRepository insuranceRepository;

    @Autowired
    UserService userService;

    /**
     * 保存保单信息
     *
     * @param insurance
     */
    public void save(Insurance insurance) {
        insuranceRepository.save(insurance);
    }

    /**
     * 分页查找保单信息
     *
     * @param pageNo
     * @param pageSize
     * @return 保单信息
     */
    public Page<Insurance> findAll(int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findAll(pageable);
    }

    /**
     * 通过用户id查找保单信息
     *
     * @param clerkId
     * @param pageNo
     * @param pageSize
     * @return 保单信息
     */
    public Page<Insurance> findAllByClerkId(int clerkId, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findAllByClerkId(clerkId, pageable);
    }

    /**
     * 通过baodanId删除保单信息，并且用户保单数减一
     * @param baodanId
     * @param userId
     */
    public void deleteById(int baodanId,int userId) {
        userService.baodanMinus(userId);
        insuranceRepository.deleteById(baodanId);
    }

    /**
     * 通过员工号删除保单信息
     * @param userId
     */
    public void deleteByClerkId(int userId) {
        insuranceRepository.deleteByClerkId(userId);
    }
    /**
     * 通过用户id查找保单信息
     *
     * @param id
     * @return 保单信息
     */
    public Insurance findById(int id) {
        return insuranceRepository.findById(id);
    }

    /**
     * 更新保单信息
     *
     * @param insurance
     */
    public void update(Insurance insurance) {
        insuranceRepository.updateInsurance(insurance);
    }

    /**
     * 意外险数量
     *
     * @return 意外险数量
     */
    public int baodan0() {
        return insuranceRepository.countByPolName(0);
    }

    /**
     * 健康险数量
     *
     * @return 健康险数量
     */
    public int baodan1() {
        return insuranceRepository.countByPolName(1);
    }

    /**
     * 补充医疗险数量
     *
     * @return 医疗险数量
     */
    public int baodan2() {
        return insuranceRepository.countByPolName(2);
    }

    /**
     * 分红险数量
     *
     * @return 分红险数量
     */
    public int baodan3() {
        return insuranceRepository.countByPolName(3);
    }

    /**
     * 所有意外险数量
     *
     * @return 所有意外险数量
     */
    public int baodan0(int id) {
        return insuranceRepository.countByPolNameAndClerkId(0, id);
    }

    /**
     * 所有健康险数量
     *
     * @return 所有健康险数量
     */
    public int baodan1(int id) {
        return insuranceRepository.countByPolNameAndClerkId(1, id);
    }

    /**
     * 所有补充医疗险数量
     *
     * @return 所有医疗险数量
     */
    public int baodan2(int id) {
        return insuranceRepository.countByPolNameAndClerkId(2, id);
    }

    /**
     * 所有分红险数量
     *
     * @return 所有分红险数量
     */
    public int baodan3(int id) {
        return insuranceRepository.countByPolNameAndClerkId(3, id);
    }

    /**
     * 所有保单数
     *
     * @return 保单数
     */
    public Long numbers() {
        return insuranceRepository.count()-1;
    }

    /**
     * 该用户的保单数
     *
     * @param id
     * @return 保单数
     */
    public Long numbers(int id) {
        return insuranceRepository.countByClerkId(id);
    }

    /**
     * 模糊查询保单
     *
     * @param content
     * @param pageNo
     * @param pageSize
     * @return 保单内容
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<Insurance> findByContent(String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findByContent(content, pageable);
    }

    /**
     * 模糊查询保单（用户自己添加的保单）
     *
     * @param content
     * @param clerkId
     * @param pageNo
     * @param pageSize
     * @return 保单信息
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<Insurance> findByClerkIdAndContent(String content, int clerkId, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findByClerkIdAndContent(content, clerkId, pageable);
    }

    /**
     * 通过分类查找保单（用户自己添加的保单）
     *
     * @param fitter
     * @param clerkId
     * @param pageNo
     * @param pageSize
     * @return 保单
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<Insurance> findByPolNameFitter(int fitter, int clerkId, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findByPolNameFitter(fitter, clerkId, pageable);
    }

    /**
     * 通过分类和模糊查询查找保单（用户自己添加的保单）
     *
     * @param fitter
     * @param clerkId
     * @param content
     * @param pageNo
     * @param pageSize
     * @return 保单
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<Insurance> findByPolNameFitterAndContent(int fitter, int clerkId, String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findByPolNameFitterAndContent(fitter, clerkId, content, pageable);
    }

    /**
     * 通过分类和模糊查询查找保单
     *
     * @param fitter
     * @param content
     * @param pageNo
     * @param pageSize
     * @return 保单信息
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<Insurance> findPolNameFitterAndContentByAdmin(int fitter, String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findPolNameFitterAndContentByAdmin(fitter, content, pageable);
    }

    /**
     * 通过分类查询查找保单
     *
     * @param fitter
     * @param pageNo
     * @param pageSize
     * @return 保单信息
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<Insurance> findPolNameFitterByAdmin(int fitter, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return insuranceRepository.findPolNameFitterByadmin(fitter, pageable);
    }
}
