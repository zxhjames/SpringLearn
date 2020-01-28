package life.james.community.controller;

import life.james.community.dto.CommentCreateDTO;
import life.james.community.dto.CommentDTO;
import life.james.community.dto.QuestionDTO;
import life.james.community.service.CommentService;
import life.james.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    //跳转到问题详情页面
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    //先拿到问题的id
    public String question(@PathVariable(name = "id") Long id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);//写回页面
        questionService.incView(id);//累加一个问题的浏览数

        //同时显示这个评论的详细内容
        List<CommentDTO> comments = commentService.listByQuestionId(id);
        model.addAttribute("comments",comments);
        return "question";
    }
}
