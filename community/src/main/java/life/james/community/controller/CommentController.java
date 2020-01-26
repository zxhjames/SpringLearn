package life.james.community.controller;

import life.james.community.dto.CommentDTO;
import life.james.community.dto.ResultDTO;
import life.james.community.exception.CustomizeErrorCode;
import life.james.community.model.Comment;
import life.james.community.model.User;
import life.james.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    //通过ResponseBody就可以直接序列化为json发到前端
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    //使用json来传递评论的信息
    //直接使用requestParam要传递太多参数，不方便,用一个对象来传递
    public Object post(@RequestBody CommentDTO commentDTO
    , HttpServletRequest request) {
        //使用flyway时出问题可以先mvn install 一下！
        User user = (User)request.getSession().getAttribute("user");
        //可能还要考虑登录的问题
        if(user==null){
            //进行错误处理,处理没有登录的情况
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setParentId(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
//        Map<Object,Object> objectObjectMap = new HashMap<>();
//        objectObjectMap.put("message","成功");
        return ResultDTO.okOf();
    }
}
