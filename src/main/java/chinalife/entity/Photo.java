package chinalife.entity;

import javax.persistence.*;

/**
 * @BelongsProject: chinalife
 * @BelongsPackage: chinalife.entity
 * @Author: Hinstein
 * @CreateTime: 2019-04-01 20:41
 * @Description: 头像实体类
 */
@Entity
@Table(name = "photo")
public class Photo {
    /**
     * 头像id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 创建时间
     */
    @Column(length = 50)
    private String datePath;

    /**
     * 文件名
     */
    @Column(length = 50)
    private String fileName;

    /**
     * 相对路径
     */
    @Column
    private String relativePath;

    /**
     * 绝对路径
     */
    @Column
    private String pathName;

    /**
     * 用户id
     */
    @Column
    private Integer userId;

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatePath() {
        return datePath;
    }

    public void setDatePath(String datePath) {
        this.datePath = datePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }
}
