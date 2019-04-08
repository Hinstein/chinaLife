package chinalife.repository;

import chinalife.entity.Recycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
