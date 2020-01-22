package life.james.community.controller;

import life.james.community.dto.PaginationDTO;
import life.james.community.mapper.UserMapper;
import life.james.community.model.User;
import life.james.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

/**
 * 用来跳转到问题和答页面
 */
@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/questions")
    public String profile(Model model, HttpServletRequest request, @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size) {

        User user= (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        model.addAttribute("section", "questions");
        model.addAttribute("sectionName", "我的提问");
        PaginationDTO pagination =questionService.listByUserId(user.getId(),page,size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }


    @GetMapping("/replies")
    public String replies(Model model, HttpServletRequest request, @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size) {


        User user= (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        model.addAttribute("section", "replies");
        model.addAttribute("sectionName", "最新回复");
        PaginationDTO pagination =questionService.listByUserId(user.getId(),page,size);
        model.addAttribute("pagination",pagination);
        return "profile";
    }
}
