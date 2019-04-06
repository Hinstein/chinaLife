package chinalife.entity;

import javax.persistence.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.entity
 * @Author: Hinstein
 * @CreateTime: 2019-03-14 12:29
 * @Description:
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String username;

    @Column(length = 50)
    private String password;

    @Column(length = 50)
    private String addTime;

    @Column(length = 50)
    private String passwordTime;

    @Column
    private int activeNumber;

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
