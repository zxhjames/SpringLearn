package life.james.community.service;

import life.james.community.mapper.UserMapper;
import life.james.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class LoginService {
    @Autowired
    private UserMapper userMapper;
    public void judge(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies==null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

    }
}
