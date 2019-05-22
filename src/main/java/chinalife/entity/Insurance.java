package chinalife.entity;

import net.bytebuddy.implementation.bytecode.assign.TypeCasting;

import javax.persistence.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.entity
 * @Author: Hinstein
 * @CreateTime: 2019-03-27 19:38
 * @Description: 保单实体类
 */
@Entity
@Table(name = "insurance")
public class Insurance {
    /**
     * 保单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 投保人姓名
     */
    @Column(length = 50)
    private String holderName;

    /**
     * 投保人性别
     */
    @Column(nullable = false)
    private int sex;

    /**
     * 投保人出生日期
     */
    @Column(length = 50)
    private String birthDate;

    /**
     * 投保人电话
     */
    @Column(length = 50)
    private String mobile;

    /**
     * 险种名称
     */
    @Column
    private int polName;

    /**
     * 投保金额
     */
    @Column
    private int money;

    /**
     * 被保人1姓名
     */
    @Column(length = 50)
    private String insuredName1;

    /**
     * 投保人与被保人1的关系
     */
    @Column(length = 50)
    private String rel1;

    /**
     * 被保人2姓名
     */
    @Column(length = 50)
    private String insuredName2;

    /**
     * 投保人与被保人2的关系
     */
    @Column(length = 50)
    private String rel2;

    /**
     * 出单时间
     */
    @Column(length = 50)
    private String inforceTime;

    /**
     * 员工号
     */
    @Column
    private int clerkId;

    /**
     * 保单id
     */
    @Column(length = 50)
    private String baodanNo;

    public String getBaodanNo() {
        return baodanNo;
    }

    public void setBaodanNo(String baodanNo) {
        this.baodanNo = baodanNo;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getInforceTime() {
        return inforceTime;
    }

    public void setInforceTime(String inforceTime) {
        this.inforceTime = inforceTime;
    }

    public int getClerkId() {
        return clerkId;
    }

    public void setClerkId(int clerkId) {
        this.clerkId = clerkId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getPolName() {
        return polName;
    }

    public void setPolName(int polName) {
        this.polName = polName;
    }

    public String getInsuredName1() {
        return insuredName1;
    }

    public void setInsuredName1(String insuredName1) {
        this.insuredName1 = insuredName1;
    }

    public String getRel1() {
        return rel1;
    }

    public void setRel1(String rel1) {
        this.rel1 = rel1;
    }

    public String getInsuredName2() {
        return insuredName2;
    }

    public void setInsuredName2(String insuredName2) {
        this.insuredName2 = insuredName2;
    }

    public String getRel2() {
        return rel2;
    }

    public void setRel2(String rel2) {
        this.rel2 = rel2;
    }
}
