package chinalife.entity;

import javax.persistence.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.entity
 * @Author: Hinstein
 * @CreateTime: 2019-04-07 12:50
 * @Description: 回收站实体类
 */
@Entity
@Table(name = "recycle")
public class Recycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String holderName;

    @Column(nullable = false)
    private int sex;

    @Column(length = 50)
    private String birthDate;

    @Column(length = 50)
    private String mobile;

    @Column
    private int polName;

    @Column
    private int money;

    @Column(length = 50)
    private String insuredName1;

    @Column(length = 50)
    private String rel1;

    @Column(length = 50)
    private String insuredName2;

    @Column(length = 50)
    private String rel2;

    @Column(length = 50)
    private String inforceTime;

    @Column
    private int clerkId;

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
