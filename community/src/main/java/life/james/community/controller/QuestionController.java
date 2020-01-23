package life.james.community.controller;

import life.james.community.dto.QuestionDTO;
import life.james.community.mapper.QuestionMapper;
import life.james.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    //跳转到问题详情页面
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question")
    //先拿到问题的id
    public String question(@RequestParam(name = "id") Integer id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);//写回页面
        questionService.incView(id);//累加一个问题的浏览数
        return "question";
    }
}
