package com.itheima.dao;

import com.itheima.domain.User;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 *
 * 用户的持久层接口
 */
public interface IUserDao {

    /**
     * 查询所有操作
     * @return
     */
    List<User> findAll();


    /**
     * 插入一个用户，并保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 删除一个用户的操作
     */
    void deleteUser(Integer id);

    /**
     * 更新操作
     */
    void updateUser(User user);
}
