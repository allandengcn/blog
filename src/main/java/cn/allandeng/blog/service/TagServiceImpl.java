package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:17
 * @Description:
 */

import cn.allandeng.blog.domain.Tag;
import cn.allandeng.blog.domain.Type;
import cn.allandeng.blog.exception.NotFoundException;
import cn.allandeng.blog.repository.TagRepository;
import cn.allandeng.blog.repository.TypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TagServiceImpl
 * @Date:2020/2/5 15:17
 * @Description: 标签服务接口实现类
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new  Sort(Sort.Direction.DESC , "blogs.size");
        Pageable pageable = new PageRequest( 0,size,sort);
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        //通过ID集合获得对象的集合
        return tagRepository.findAll(convertToList(ids));
    }

    /**
     * @Title convertToList
     * @Author Allan Deng
     * @Description  将标签id的字符串转换为标签List
     * @Date 12:45 2020/2/10
     * @Param [ids]
     * @return java.util.List<java.lang.Long>
     **/
    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) { //非空判断
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findOne(id);
        //若找不到分类则抛出异常
        if (t == null){
            throw new NotFoundException("找不到指定分类");
        }
        //复制参数，传入的tag中没有id属性，而t中有id属性
        BeanUtils.copyProperties(tag,t);
        //因为有id属性，直接更新
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }
}
