package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/7 18:03
 * @Description:
 */

import cn.allandeng.blog.domain.Comment;

import java.util.List;

/**
 * @ClassName CommentService
 * @Date:2020/2/7 18:03
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface CommentService {

    //通过id获得评论
    Comment getComment(Long id);

    //通过id删除评论
    void deleteComment(Long id);

    //通过博客id获得评论列表
    List<Comment> listCommentByBlogId(Long blogId);

    //保存评论
    Comment saveComment(Comment comment);
}
