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
     * 根据编号查询一个用户
     */
    User getUserById(Integer id);

    /**
     * 根据姓名进行模糊查询
     */
    List<User> getUserByusername(String username);

    /**
     * 根据QueryVo查询中的条件查询用户
     * @param vo
     * @return
     */
    List<User> findUserByVo(QueryVo vo);

    /**
     * 根据传入的参数条件进行查询
     * @param user 查询的条件,有可能有用户名,有可能有性别,也有可能有地址,也有可能都没有
     * @return
     */
    List<User> findUserByCondition(User user);


    /**
     * 根据queryvo中提供的id集合，查询用户信息
     * @param vo
     * @return
     */
    List<User> findUserInIds(QueryVo vo);
}
