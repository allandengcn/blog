package cn.allandeng.blog.repository;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:18
 * @Description:
 */

import cn.allandeng.blog.domain.Type;
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
public interface TypeRepository extends JpaRepository<Type,Long> {
    //按照名称查找类型
    Type findByName(String name);

    //查找博客数量最多的分类
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
