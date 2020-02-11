package cn.allandeng.blog.repository;
/**
 * @Auther: Allan
 * @Date: 2020/2/6 12:17
 * @Description:
 */

import cn.allandeng.blog.domain.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName BlogRepository
 * @Date:2020/2/6 12:17
 * @Description: Blog类DAO
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface BlogRepository extends JpaRepository<Blog,Long> , JpaSpecificationExecutor<Blog> {

    //查找推荐文章
    @Query("select b from Blog b where b.recommend = true ")
    List<Blog> findRecommendTop(Pageable pageable);

    //查找已发布文章
    @Query("select b from Blog b where b.published = true ")
    Page<Blog> findPublished(Pageable pageable);

    //按条件查找文章
    @Query("select b from Blog b where b.title like ?1 or  b.content like ?1")
    Page<Blog> findByQuery(String query ,Pageable pageable);

    //查找年份
    @Query("select function('date_format',b.createTime,'%Y') as year from Blog b group by function('date_format',b.createTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    //查找某年的文章create
    @Query("select b from Blog b where function('date_format',b.createTime,'%Y') = ?1")
    List<Blog> findByYear(String year, Sort sort);


}
