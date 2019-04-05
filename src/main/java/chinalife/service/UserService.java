package chinalife.service;


import chinalife.entity.User;
import chinalife.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public void changePassword(String password,int id){userRepository.changePassword(password,id);}

    @Transactional
    public Page<User> findByContent(String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.findBycontent(content, pageable);
    }

    public void changeInformation(String username,int id){userRepository.changeInformation(username,id);}
}
