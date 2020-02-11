package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/5 12:37
 * @Description:
 */

import cn.allandeng.blog.domain.User;
import cn.allandeng.blog.repository.UserRepository;
import cn.allandeng.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Date:2020/2/5 12:37
 * @Description: 用户服务实现类
 * @Author: Allan Deng
 * @Version: 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
