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
 * @Description: 保单dao层
 */
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {

    /**
     * 分页查找所有保单信息
     *
     * @param pageable
     * @return 保单信息
     */
    @Override
    Page<Insurance> findAll(Pageable pageable);

    /**
     * 分页通过员工号查找保单信息
     *
     * @param id
     * @param pageable
     * @return 保单信息
     */
    Page<Insurance> findAllByClerkId(int id, Pageable pageable);

    /**
     * 通过保单id删除保单信息
     *
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过保单id查找保单信息
     *
     * @param id
     * @return 保单信息
     */
    Insurance findById(int id);

    /**
     * 更新保单信息
     *
     * @param insurance
     */
    @Transactional(rollbackOn = Exception.class)
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
    void updateInsurance(Insurance insurance);

    /**
     * 计算某保单类型总数量
     *
     * @param id
     * @return 保单总数量
     */
    int countByPolName(int id);

    /**
     * 计算该用户的某类型保单数量
     *
     * @param id
     * @param clerkId
     * @return 该用户的保单数量
     */
    int countByPolNameAndClerkId(int id, int clerkId);

    /**
     * 所有保单数量
     *
     * @return 保单数量
     */
    @Override
    long count();

    /**
     * 计算保单类型数量
     *
     * @param clerkId
     * @return 某类型的保单数量
     */
    long countByClerkId(int clerkId);

    /**
     * 模糊查询保单信息
     *
     * @param content
     * @param pageable
     * @return 保单信息
     */
    @Query(value = "select u from Insurance u where u.clerkId like CONCAT('%',?1,'%') or u.holderName like CONCAT('%',?1,'%') or u.mobile like CONCAT('%',?1,'%')")
    Page<Insurance> findByContent(String content, Pageable pageable);

    /**
     * 模糊查询保单信息（用户自己录用的保单信息）
     *
     * @param content
     * @param clerkId
     * @param pageable
     * @return 保单信息
     */
    @Query(value = "select u from Insurance u where  u.clerkId = ?2 and (u.holderName like CONCAT('%',?1,'%') or u.mobile like CONCAT('%',?1,'%') )")
    Page<Insurance> findByClerkIdAndContent(String content, int clerkId, Pageable pageable);

    /**
     * 分类查询保单信息（用户自己录用的保单信息）
     *
     * @param fitter
     * @param clerkId
     * @param pageable
     * @return 保单信息
     */
    @Query(value = "select u from Insurance u where  u.clerkId= ?2 and u.polName = ?1")
    Page<Insurance> findByPolNameFitter(int fitter, int clerkId, Pageable pageable);

    /**
     * 分类查询保单信息
     *
     * @param fitter
     * @param pageable
     * @return 保单信息
     */
    @Query(value = "select u from Insurance u where  u.polName = ?1")
    Page<Insurance> findPolNameFitterByadmin(int fitter, Pageable pageable);

    /**
     * 分类和模糊查询保单（用户自己录用的保单信息）
     *
     * @param fitter
     * @param clerkId
     * @param content
     * @param pageable
     * @return 保单信息
     */
    @Query(value = "select u from Insurance u where  u.clerkId = ?2 and u.polName = ?1 and (u.holderName like CONCAT('%',?3,'%') or u.mobile like CONCAT('%',?3,'%') )")
    Page<Insurance> findByPolNameFitterAndContent(int fitter, int clerkId, String content, Pageable pageable);

    /**
     * 分类和模糊查询保单
     *
     * @param fitter
     * @param content
     * @param pageable
     * @return 保单信息
     */
    @Query(value = "select u from Insurance u where  u.polName = ?1 and (u.holderName like CONCAT('%',?2,'%') or u.mobile like CONCAT('%',?2,'%') )")
    Page<Insurance> findPolNameFitterAndContentByAdmin(int fitter, String content, Pageable pageable);
}
