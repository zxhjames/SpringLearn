package com.itheima.test;

import com.itheima.dao.IUserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 *
 * 测试mybatis的crud操作
 */
public class MybatisTest {

    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//用于在测试方法执行之前执行
    public void init() throws Exception {
        //1.读取配置文件，生成字节输入流
        in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取SqlSession对象
        sqlSession = factory.openSession();
        //4.获取dao的代理对象

        //1第一种方法
//        userDao = sqlSession.getMapper(IUserDao.class);
        userDao = new UserDaoImpl(factory);
    }

    @After//用于在测试方法执行之后执行
    public void destroy() throws Exception {
//        //提交事务
//        sqlSession.commit();
//        //6.释放资源
//        sqlSession.close();
        in.close();
    }


    /***
     * 基本的crud操作
     */

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() {
        //5.执行查询所有方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }

    }

    /**
     * 测试保存操作(插入操作)
     */
    @Test
    public void testSave() {
        User user = new User();
        user.setUsername("吴恩达");
        user.setAddress("北京市顺义区");
        user.setSex("m");
        user.setBirthday(new Date());
        userDao.saveUser(user);
    }

    /**
     * 删除一个用户
     */
    @Test
    public void testDelete(){
        userDao.deleteUser(2);
    }

    /**
     * 更新一个用户的信息
     */
    @Test
    public void testUpdate(){
        //可能是数据库已有的对象，也可能是数据库中查找得到的对象
        User user = new User();
        user.setId(2);
        user.setUsername("james");
        user.setAddress("洛杉矶");
        user.setSex("男");
        user.setBirthday(new Date());
        userDao.updateUser(user);
    }

    /***
     * 根据用户的编号返回一个用户信息
     */
    @Test
    public void testgetUserById(){
        Integer id = 4;
        User user = userDao.getUserById(id);
        if(user!=null){
            System.out.println(user.toString());
        }
    }

    /**
     * 模糊查询
     */
    @Test
    public void testgetUserByuname(){
        List<User> user = userDao.getUserByusername("%o%");
        for (User u : user){
            System.out.println(u.toString());
        }
    }

    /**
     * 返回记录条数
     */
    @Test
    public void getUserNum(){
        Integer n = userDao.getUserNum();
        System.out.println(n+"条记录");
    }

    @Test
    //测试使用QueryVo作为查询条件
    public void testFindByVo(){
        //由多个对象完成数据的查询
        //例如select * from where para1 = ... and para2 = ..."
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%o%");
        vo.setUser(user);
        List<User> users = userDao.findUserByVo(vo);
        for(User u : users){
            System.out.println(u);
        }
    }
}