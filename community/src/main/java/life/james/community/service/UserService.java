package life.james.community.service;


import life.james.community.mapper.UserMapper;
import life.james.community.model.User;
import life.james.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Component
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    HttpServletRequest request;

    public void CreatOrUpdate(User user) {
        //重构
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountidEqualTo(user.getAccountid());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if(dbUsers.size()==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
//            request.getSession().setAttribute("user",user);
            userMapper.insert(user);
        }else{
            //更新
            User u = dbUsers.get(0);//抽取查找到第一个
            u.setGmtCreate(System.currentTimeMillis());
            u.setGmtModified(user.getGmtCreate());
            u.setName(user.getName());
            u.setToken(user.getToken());

            //重构
            User updateUser = new User();
            updateUser.setGmtCreate(System.currentTimeMillis());
            updateUser.setGmtModified(user.getGmtCreate());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(u.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
