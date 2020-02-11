package cn.allandeng.blog.domain;
/**
 * @Auther: Allan
 * @Date: 2020/2/5 11:29
 * @Description:
 */

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Type
 * @Date:2020/2/5 11:29
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    //分类名称
    @NotBlank(message = "分类名称不能为空")
    @Column(unique=true)
    private String name;

    //属于该分类的博客列表
    //mappedBy 关系被维护
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
