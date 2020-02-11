package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:15
 * @Description:
 */

import cn.allandeng.blog.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName TagService
 * @Date:2020/2/5 15:15
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface TagService {


    //保存标签
    Tag saveTag(Tag type);

    //获取标签
    Tag getTag(Long id);

    //使用名称获取标签
    Tag getTagByName(String name);

    //按分页获取标签
    Page<Tag> listTag(Pageable pageable);

    //获取所有标签
    List<Tag> listTag();

    //获取指定数量的博客数最多的标签
    List<Tag> listTagTop( Integer size);

    //使用形如“1,2,3”的字符串活动的Tag列表
    List<Tag> listTag(String ids);

    //更新标签
    Tag updateTag(Long id, Tag type);

    //删除标签
    void deleteTag(Long id);

}
