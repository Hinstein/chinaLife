package chinalife.entity;

import javax.persistence.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.entity
 * @Author: Hinstein
 * @CreateTime: 2019-03-17 21:04
 * @Description: 权限实体类
 */
@Table(name = "permission")
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String perms;

    @Column
    private int userId;

    @Column(length = 50)
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }
}
