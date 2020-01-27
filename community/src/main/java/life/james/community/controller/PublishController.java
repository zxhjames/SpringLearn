package life.james.community.controller;
import life.james.community.dto.QuestionDTO;
import life.james.community.mapper.QuestionMapper;
import life.james.community.mapper.UserMapper;
import life.james.community.model.Question;
import life.james.community.model.User;
import life.james.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;//自动注入

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;


    //新发布一个问题
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    //跳转到问题的详细页面
    @GetMapping("/publish/{id}")
    public String EditPublish(@PathVariable(name = "id") long id
    , Model model){
        QuestionDTO question=questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }





    //接受参数,发布一个新的问题,发布成功后跳转到根页面,根页面会显示最新的内容
    @PostMapping("/publish")
    public String doPublish(
            //传入publish页面发送过来的值
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model) {
        //存放如model中,用于回显到页面上
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        //防止用户的输入不完全
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        } else if (description == null || description == "") {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        } else if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user= (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);//新增加的
        questionService.CreateOrUpdate(question);//重新更新或者是创建问题
        return "redirect:/";

    }
}
