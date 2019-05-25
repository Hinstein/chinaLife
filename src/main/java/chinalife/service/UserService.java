package chinalife.service;


import chinalife.entity.User;
import chinalife.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.service
 * @Author: Hinstein
 * @CreateTime: 2019-03-14 12:35
 * @Description: 用户service
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecycleService recycleService;

    @Autowired
    InsuranceService insuranceService;

    /**
     * 通过用户名查找用户
     *
     * @param username
     * @return 用户信息
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 分页查找所有用户
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<User> findAll(int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findAll(pageable);
    }

    /**
     * 通过id查找用户
     *
     * @param id
     * @return
     */
    public User findById(int id) {
        return userRepository.findById(id);
    }

    /**
     * 保存用户
     *
     * @param user
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * 通过id删除用户
     *
     * @param id
     */
    public void deleteById(int id) {
        insuranceService.deleteByClerkId(id);
        recycleService.deleteByClerkId(id);
        userRepository.deleteById(id);
    }

    /**
     * 更新用户密码
     *
     * @param password
     * @param id
     * @param time
     */
    public void changePassword(String password, int id, String time) {
        userRepository.changePassword(password, id, time);
    }

    /**
     * 分页模糊查询用户信息
     *
     * @param content
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<User> findByContent(String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findBycontent(content, pageable);
    }

    /**
     * 更新用户信息
     *
     * @param username
     * @param id
     */
    public void changeInformation(String username, int id) {
        userRepository.changeInformation(username, id);
    }

    /**
     * 用户活跃量加一
     *
     * @param id
     */
    public void active(int id) {
        userRepository.active(id);
    }

    /**
     * 用户保单数加一
     *
     * @param id
     */
    public void baodanPlus(int id) {
        userRepository.baodanPlus(id);
    }

    /**
     * 用户保单数减一
     *
     * @param id
     */
    public void baodanMinus(int id) {
        userRepository.baodanMinus(id);
    }

    /**
     * 用户活跃榜单
     *
     * @return 榜单信息
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<User> activeRank() {
        Sort sort = new Sort(Sort.Direction.DESC, "activeNumber");
        PageRequest pageable = PageRequest.of(0, 5, sort);
        return userRepository.activeRank(pageable);
    }

    /**
     * 用户业绩榜单
     *
     * @return 榜单信息
     */
    @Transactional(rollbackOn = Exception.class)
    public Page<User> baodanRank() {
        Sort sort = new Sort(Sort.Direction.DESC, "baodanNumber");
        PageRequest pageable = PageRequest.of(0, 5, sort);
        return userRepository.baodanRank(pageable);
    }


    /**
     * 更新用户保单数
     *
     * @param baodanNumber
     * @param userId
     */
    public void changeBaodanNumber(int baodanNumber, int userId) {
        userRepository.changeBaodanNumber(baodanNumber, userId);
    }


    /**
     * 所有员工数量
     *
     * @return 员工数量
     */
    public Long numbers() {
        return userRepository.count() - 1;
    }
}
