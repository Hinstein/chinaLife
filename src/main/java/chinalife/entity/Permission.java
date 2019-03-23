package chinalife.entity;

import javax.persistence.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.entity
 * @Author: Hinstein
 * @CreateTime: 2019-03-17 21:04
 * @Description:
 */
@Table(name = "permission")
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String perms;

    @Column
    private int userId;

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
