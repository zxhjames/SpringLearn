package life.james.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//允许这个类去接受前端的一个请求
@Controller
public class IndexController {
    @GetMapping("/")
    //显示主页
    public String hello(){
        return "index";//去template找
    }
}
