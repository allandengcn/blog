package cn.allandeng.blog.repository;/**
 * @Auther: Allan
 * @Date: 2020/2/7 18:05
 * @Description:
 */

import cn.allandeng.blog.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName CommentRepository
 * @Date:2020/2/7 18:05
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface CommentRepository extends JpaRepository<Comment,Long> {

    //按照博客id查找顶级评论
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
