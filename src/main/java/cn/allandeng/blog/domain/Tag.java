package cn.allandeng.blog.domain;
/**
 * @Auther: Allan
 * @Date: 2020/2/5 11:32
 * @Description:
 */

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Tag
 * @Date:2020/2/5 11:32
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Entity
@Table(name = "t_tag")
public class Tag{

    @Id
    @GeneratedValue
    private Long id;

    //标签名称
    @NotBlank(message = "标签名称不能为空")
    @Column(unique=true)
    private String name;

    //拥有该标签的博客列表
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
