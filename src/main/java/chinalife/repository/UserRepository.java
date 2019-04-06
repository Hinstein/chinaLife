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
    User findByUsername(String username);

    User findById(int id);

    @Override
    @Query("select user  from User user where user.username<>'admin'")
    Page<User> findAll(Pageable pageable);

    void deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user  SET user.password=?1 , user.passwordTime= ?3 " +
            "WHERE user.id = ?2")
    void changePassword(String password, int id, String time);

    @Query(value = "select u from User u where u.username like CONCAT('%',?1,'%') or u.id like CONCAT('%',?1,'%')")
    Page<User> findBycontent(String content, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user  SET user.username=?1 " +
            "WHERE user.id = ?2")
    void changeInformation(String username, int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user  SET user.activeNumber= user.activeNumber+1" +
            "WHERE user.id = ?1")
    void active(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User user  SET user.baodanNumber= user.baodanNumber+1" +
            "WHERE user.id = ?1")
    void baodanNumber(int id);

    @Query("select user  from User user where user.username<>'admin'")
    Page<User> activeRank(Pageable pageable);

    @Query("select user  from User user where user.username<>'admin'")
    Page<User> baodanRank(Pageable pageable);
}
