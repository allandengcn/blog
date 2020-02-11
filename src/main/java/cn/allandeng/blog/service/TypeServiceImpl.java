package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/5 15:17
 * @Description:
 */

import cn.allandeng.blog.domain.Type;
import cn.allandeng.blog.exception.NotFoundException;
import cn.allandeng.blog.repository.TypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @ClassName TypeServiceImpl
 * @Date:2020/2/5 15:17
 * @Description: 分类服务接口实现类
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new  Sort(Sort.Direction.DESC , "blogs.size");
        Pageable pageable = new PageRequest( 0,size,sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findOne(id);
        //若找不到分类则抛出异常
        if (t == null){
            throw new NotFoundException("找不到指定分类");
        }
        //复制参数，传入的type中没有id属性，而t中有id属性
        BeanUtils.copyProperties(type,t);
        //因为有id属性，直接更新
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }
}
