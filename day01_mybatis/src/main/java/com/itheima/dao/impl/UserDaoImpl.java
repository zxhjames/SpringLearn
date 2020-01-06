package com.itheima.dao.impl;

import com.itheima.dao.IUserDao;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserDaoImpl implements IUserDao {
    private SqlSessionFactory factory;

    public UserDaoImpl(SqlSessionFactory factory){
        this.factory = factory;
    }

    //查询所有记录
    @Override
    public List<User> findAll() {
        //1.根据factory获取sqlsession对象
        SqlSession session = factory.openSession();
        //2.调用SqlSession中的方法，实现查询列表
        List<User> users = session.selectList("com.itheima.dao.IUserDao.findAll");//实现配置信息的key
        session.close();
        return users;
    }

    //插入一条记录
    @Override
    public void saveUser(User user) {
        SqlSession session = factory.openSession();
        session.insert("com.itheima.dao.IUserDao.saveUser",user);
        session.commit();
        session.close();
    }

    //根据id号删除一条记录
    @Override
    public void deleteUser(Integer id) {
        //根据factory获取SqlSession请求
        SqlSession session = factory.openSession();
        //调用方法实现更新
        session.delete("com.itheima.dao.IUserDao.deleteUser",id);
        //提交事务
        session.commit();
        //释放资源
        session.close();
    }

    //更新一个user的信息
    @Override
    public void updateUser(User user) {
        SqlSession session = factory.openSession();
        session.update("com.itheima.dao.IUserDao.updateUser",user);
        session.commit();
        session.close();

    }

    //根据id查询单条记录
    @Override
    public User getUserById(Integer id) {
        SqlSession session = factory.openSession();
        User user = session.selectOne("com.itheima.dao.IUserDao.getUserById",id);
        session.close();
        return user;
    }


    //根据username获取user序列
    @Override
    public List<User> getUserByusername(String username) {
        SqlSession session = factory.openSession();
        List<User> users = session.selectList("com.itheima.dao.IUserDao.getUserByusername",username);
        session.close();
        return users;
    }

    @Override
    public int getUserNum() {
        SqlSession session = factory.openSession();
        Integer nums = session.selectOne("com.itheima.dao.IUserDao.getUserNum");
        session.close();
        return nums;
    }

    @Override
    public List<User> findUserByVo(QueryVo vo) {
        return null;
    }
}

