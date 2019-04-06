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
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Page<User> findAll(int pageNo, int pageSize){
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findAll(pageable);
    }

    public User findById(int id){
        return userRepository.findById(id);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    public void changePassword(String password,int id,String time){userRepository.changePassword(password,id,time);}

    @Transactional
    public Page<User> findByContent(String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findBycontent(content, pageable);
    }

    public void changeInformation(String username,int id){userRepository.changeInformation(username,id);}

    public void active(int id){
        userRepository.active(id);
    }

    public void  baodanNumber(int id){
        userRepository.baodanNumber(id);
    }


    @Transactional
    public Page<User> activeRank() {
        Sort sort = new Sort(Sort.Direction.DESC, "activeNumber");
        PageRequest pageable = PageRequest.of(0 , 5,sort);
        return userRepository.activeRank(pageable);
    }

    @Transactional
    public Page<User> baodanRank() {
        Sort sort = new Sort(Sort.Direction.DESC, "baodanNumber");
        PageRequest pageable = PageRequest.of(0 , 5,sort);
        return userRepository.baodanRank(pageable);
    }
}
