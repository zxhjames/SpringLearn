package com.itheima.dao;

import com.itheima.domain.QueryVo;
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

    /**
     * 根据编号查询一个用户
     */
    User getUserById(Integer id);

    /**
     * 根据姓名进行模糊查询
     */
    List<User> getUserByusername(String username);

    /**
     * 查询总记录条数
     */
    int getUserNum();


    /**
     * 根据QueryVo查询中的条件查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);
}
