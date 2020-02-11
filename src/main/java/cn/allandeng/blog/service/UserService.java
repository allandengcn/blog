package cn.allandeng.blog.service;/**
 * @Auther: Allan
 * @Date: 2020/2/5 12:33
 * @Description:
 */

import cn.allandeng.blog.domain.User;

/**
 * @ClassName UserService
 * @Date:2020/2/5 12:33
 * @Description: 用户服务接口
 * @Author: Allan Deng
 * @Version: 1.0
 **/
public interface UserService {

    /**
     * @Title checkUser
     * @Author Allan Deng
     * @Description 检查是否存在用户
     * @Date 12:36 2020/2/5
     * @Param [username, password]
     * @return cn.allandeng.blog.domain.User
     **/
    User checkUser(String username, String password);
}
