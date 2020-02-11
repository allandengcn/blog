package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/6 12:18
 * @Description:
 */

import cn.allandeng.blog.domain.Blog;
import cn.allandeng.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BlogService
 * @Date:2020/2/6 12:18
 * @Description: Blog服务类
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface BlogService {
    //按照id获得博客
    Blog getBlog(Long id);

    //按照id获得博客并转化markdown为HTML
    Blog getAndConvertBlog(Long id);

    //按照查询条件和分页搜索博客
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    //按照分页搜索博客
    Page<Blog> listBlog(Pageable pageable);

    //按照关键字和分页搜索博客
    Page<Blog> listBlog(String query,Pageable pageable);

    //增加博客阅读数
    Blog getBlogIncreaseViews(Long id);

    //获取推荐博客的最新列表
    List<Blog> listRecommendBlogTop(Integer size);

    //按照标签id获得博客
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    //保存博客
    Blog saveBlog(Blog blog);

    //更新博客
    Blog updateBlog(Long id,Blog blog);

    //删除博客
    void deleteBlog(Long id);

    //归档博客
    Map<String,List<Blog>> archiveBlog();

    //博客计数
    Long countBlog();
}
