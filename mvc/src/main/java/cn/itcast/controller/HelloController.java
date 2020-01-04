package cn.itcast.controller;
//控制器类

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//把这个类交给aoc处理
@Controller
public class HelloController {
    @RequestMapping(path = "/hello")
    public String sayHello(){
        return "success";
    }
}
