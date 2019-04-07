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
    void deleteById(int id);

    Page<Recycle> findAllByClerkId(int id, Pageable pageable);

    Recycle findById(int id);
}
