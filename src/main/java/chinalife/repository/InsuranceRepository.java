package chinalife.repository;

import chinalife.entity.Insurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.repository
 * @Author: Hinstein
 * @CreateTime: 2019-03-27 19:57
 * @Description:
 */
public interface InsuranceRepository extends JpaRepository<Insurance,Integer> {
 @Override
    Page<Insurance> findAll(Pageable pageable);
}
