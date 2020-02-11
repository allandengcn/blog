package cn.allandeng.blog.repository;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:18
 * @Description:
 */

import cn.allandeng.blog.domain.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName TypeRepository
 * @Date:2020/2/5 15:18
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface TagRepository extends JpaRepository<Tag,Long> {
    //按名称查找标签
    Tag findByName(String name);

    //查找博客数量最多的标签
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
