package chinalife.repository;

import chinalife.entity.Insurance;
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
 * @CreateTime: 2019-03-27 19:57
 * @Description:
 */
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    @Override
    Page<Insurance> findAll(Pageable pageable);

    Page<Insurance> findAllByClerkId(int id, Pageable pageable);

    void deleteById(int id);

    Insurance findById(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Insurance i  SET i.birthDate= :#{#insurance.birthDate}," +
            "i.holderName= :#{#insurance.holderName}," +
            "i.insuredName1= :#{#insurance.insuredName1}," +
            "i.insuredName2= :#{#insurance.insuredName2}," +
            "i.mobile= :#{#insurance.mobile}," +
            "i.money= :#{#insurance.money}," +
            "i.polName= :#{#insurance.polName}," +
            "i.rel1= :#{#insurance.rel1}," +
            "i.rel2= :#{#insurance.rel2}," +
            "i.sex= :#{#insurance.sex} " +
            "WHERE i.id = :#{#insurance.id}")
    void updateInsruance(Insurance insurance);
}
