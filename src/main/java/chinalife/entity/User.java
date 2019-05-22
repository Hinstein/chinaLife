package chinalife.entity;

import javax.persistence.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.entity
 * @Author: Hinstein
 * @CreateTime: 2019-03-14 12:29
 * @Description: 用户实体类
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(length = 50)
    private String username;

    /**
     * 密码
     */
    @Column(length = 50)
    private String password;

    /**
     * 账号添加时间
     */
    @Column(length = 50)
    private String addTime;

    /**
     * 密码修改时间
     */
    @Column(length = 50)
    private String passwordTime;

    /**
     * 活跃度
     */
    @Column
    private int activeNumber;

    /**
     * 保单数
     */
    @Column
    private int baodanNumber;

    public int getActiveNumber() {
        return activeNumber;
    }

    public void setActiveNumber(int activeNumber) {
        this.activeNumber = activeNumber;
    }

    public int getBaodanNumber() {
        return baodanNumber;
    }

    public void setBaodanNumber(int baodanNumber) {
        this.baodanNumber = baodanNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getPasswordTime() {
        return passwordTime;
    }

    public void setPasswordTime(String passwordTime) {
        this.passwordTime = passwordTime;
    }
}
