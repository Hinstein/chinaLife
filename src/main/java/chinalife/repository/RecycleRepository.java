package chinalife.repository;

import chinalife.entity.Recycle;
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
 * @CreateTime: 2019-04-07 12:52
 * @Description:
 */
public interface RecycleRepository extends JpaRepository<Recycle, Integer> {
    /**
     * 通过id删除信息
     *
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过员工id删除回收站信息
     * @param id
     */
    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "delete from Recycle a where a.clerkId = ?1 ")
    void deleteByClerkId(int id);

    /**
     * 通过业务员工号找到信息
     *
     * @param id
     * @param pageable
     * @return
     */
    Page<Recycle> findAllByClerkId(int id, Pageable pageable);

    /**
     * 通过id找到信息
     *
     * @param id
     * @return
     */
    Recycle findById(int id);
}
