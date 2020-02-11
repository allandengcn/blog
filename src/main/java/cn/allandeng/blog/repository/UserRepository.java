package cn.allandeng.blog.repository;/**
 * @Auther: Allan
 * @Date: 2020/2/5 12:38
 * @Description:
 */

import cn.allandeng.blog.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName User
 * @Date:2020/2/5 12:38
 * @Description:
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface UserRepository extends JpaRepository<User, Long> {

    //按用户名和密码查找用户
    User findByUsernameAndPassword(String username , String password);

}
