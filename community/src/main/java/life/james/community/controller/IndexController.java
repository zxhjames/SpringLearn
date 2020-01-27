package life.james.community.controller;


import life.james.community.dto.PaginationDTO;
import life.james.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;

//允许这个类去接受前端的一个请求
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService; //获取博客问题列表
    @GetMapping("/")
    //显示主页
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "4") Integer size)
    {
        //拿到服务器的cookie,先去cookie中寻找token,再拿得到的token与数据库中的token做比较
        //如果有那么就存入session,没有则跳转到登录页面


        PaginationDTO pagination = questionService.list(page,size);
//        Collections.reverse(pagination.getQuestions());
        model.addAttribute("pagination",pagination);
        return "index";//去template找
    }
}
