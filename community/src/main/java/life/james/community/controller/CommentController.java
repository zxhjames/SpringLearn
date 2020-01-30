package life.james.community.controller;

import life.james.community.dto.CommentCreateDTO;
import life.james.community.dto.ResultDTO;
import life.james.community.exception.CustomizeErrorCode;
import life.james.community.model.Comment;
import life.james.community.model.User;
import life.james.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO
            , HttpServletRequest request) {
        //使用flyway时出问题可以先mvn install 一下！
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            System.out.println(user.getId());
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
//        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
//            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_NOT_FOUND);
//        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(String.valueOf(commentCreateDTO.getContent()));
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtCreate(comment.getGmtCreate());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
