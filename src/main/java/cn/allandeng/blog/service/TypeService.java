package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:15
 * @Description:
 */

import cn.allandeng.blog.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName TypeService
 * @Date:2020/2/5 15:15
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface TypeService {

    //保存分类
    Type saveType(Type type);

    //获取分类
    Type getType(Long id);

    //依照名称获取分类
    Type getTypeByName(String name);

    //分页获取评论列表
    Page<Type> listType(Pageable pageable);

    //获取所有分类
    List<Type> listType();

    //获取博客最多的分类
    List<Type> listTypeTop(Integer size);

    //更新分类
    Type updateType(Long id ,Type type);

    //更新分类
    void deleteType(Long id);

}
