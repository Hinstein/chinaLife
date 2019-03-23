package chinalife.repository;


import chinalife.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
    @Query("select user  from User  user where user.username<>'admin'")
    Page<User> findAll(Pageable pageable);

    void deleteById(int id);
}