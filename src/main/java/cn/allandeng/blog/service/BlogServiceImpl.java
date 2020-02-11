package cn.allandeng.blog.service;
/**
 * @Auther: Allan
 * @Date: 2020/2/6 12:20
 * @Description:
 */

import cn.allandeng.blog.domain.Blog;
import cn.allandeng.blog.domain.Type;
import cn.allandeng.blog.exception.NotFoundException;
import cn.allandeng.blog.repository.BlogRepository;
import cn.allandeng.blog.util.MyBeanUtils;
import cn.allandeng.blog.util.markdownUtil.MarkdownUtils;
import cn.allandeng.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @ClassName BlogServiceImpl
 * @Date:2020/2/6 12:20
 * @Description: 博客服务的实现类
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Service
public class BlogServiceImpl implements BlogService {


    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    public Blog getAndConvertBlog(Long id) {
        //实现了浏览数的增加
        Blog blog = getBlogIncreaseViews(id);
        if (blog == null){
            throw new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            /*
            * Root<Blog>,Blog的表结构
            * CriteriaQuery<?>，需要查询的参数
            * CriteriaBuilder，查询表达式
            * */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //predicates中为需要查询的参数
                List<Predicate> predicates = new ArrayList<>();
                //添加博客名的like搜索，需要判断是否为空支付串 或者 null
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //向predicates中添加元素，从root中获取需要查询的内容，表达式需要添加%，已实现like
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //使用cq开始查询 where中后添加条件数组，需要将List转换为Array
                cq.where(predicates.toArray(new Predicate[predicates.size()]));

                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query , Pageable pageable) {
        return blogRepository.findByQuery(query , pageable);
    }

    @Override
    public Blog getBlogIncreaseViews(Long id) {
        Blog blog = blogRepository.findOne(id);
        blog.setViews(blog.getViews()+1);
        blogRepository.save(blog);
        return blog;
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new  Sort(Sort.Direction.DESC , "updateTime");
        Pageable pageable = new PageRequest( 0,size,sort);
        return blogRepository.findRecommendTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //当第一次创建时需要初始化一些值
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            //以后每次修改都会更新
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        //其中id对应的是数据库中的blog对象
        //传入的blog对象是需要存储的blog对象
        Blog b = blogRepository.findOne(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        //布置blog中的参数到b，只copy有值的参数
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Collections.sort(years, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o2) - Integer.valueOf(o1);
            }
        });
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year,sort));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }
}
