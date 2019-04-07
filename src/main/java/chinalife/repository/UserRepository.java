package chinalife.repository;


import chinalife.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.repository
 * @Author: Hinstein
 * @CreateTime: 2019-03-14 12:34
 * @Description:
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 通过姓名查找用户
     *
     * @param username
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 通过id查找用户信息
     *
     * @param id
     * @return 用户信息
     */
    User findById(int id);

    /**
     * 查找所有用户（不包括管理员）
     *
     * @param pageable
     * @return 所有用户
     */
    @Override
    @Query("select user  from User user where user.username<>'admin'")
    Page<User> findAll(Pageable pageable);

    /**
     * 通过id删除用户信息
     *
     * @param id
     */
    void deleteById(int id);

    /**
     * 更新密码信息
     *
     * @param password
     * @param id
     * @param time
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "UPDATE User user  SET user.password=?1 , user.passwordTime= ?3 " +
            "WHERE user.id = ?2")
    void changePassword(String password, int id, String time);

    /**
     * 模糊查询用户信息
     *
     * @param content
     * @param pageable
     * @return 用户信息
     */
    @Query(value = "select u from User u where u.username like CONCAT('%',?1,'%') or u.id like CONCAT('%',?1,'%')")
    Page<User> findBycontent(String content, Pageable pageable);

    /**
     * 更新用户信息
     *
     * @param username
     * @param id
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "UPDATE User user  SET user.username=?1 " +
            "WHERE user.id = ?2")
    void changeInformation(String username, int id);

    /**
     * 用户活跃度加一
     *
     * @param id
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "UPDATE User user  SET user.activeNumber= user.activeNumber+1" +
            "WHERE user.id = ?1")
    void active(int id);

    /**
     * 用户保单数加一
     *
     * @param id
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "UPDATE User user  SET user.baodanNumber= user.baodanNumber+1" +
            "WHERE user.id = ?1")
    void baodanNumber(int id);

    /**
     * 通过用户活跃量排名查找
     *
     * @param pageable
     * @return
     */
    @Query("select user  from User user where user.username<>'admin'")
    Page<User> activeRank(Pageable pageable);

    /**
     * 通过用户业绩排名查找
     *
     * @param pageable
     * @return
     */
    @Query("select user  from User user where user.username<>'admin'")
    Page<User> baodanRank(Pageable pageable);

    /**
     * 更新用户保单数据
     *
     * @param baodanNumber
     * @param id
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "UPDATE User user  SET user.baodanNumber= ?1 " +
            "WHERE user.id = ?2")
    void changeBaodanNumber(int baodanNumber, int id);
}
