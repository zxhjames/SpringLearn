package life.james.community.service;


import life.james.community.dto.CommentDTO;
import life.james.community.enums.CommentTypeEnum;
import life.james.community.exception.CustermizeException;
import life.james.community.exception.CustomizeErrorCode;
import life.james.community.mapper.CommentMapper;
import life.james.community.mapper.QuestionExtMapper;
import life.james.community.mapper.QuestionMapper;
import life.james.community.mapper.UserMapper;
import life.james.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    //插入评论
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            System.out.println("错误1");
            throw new CustermizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            System.out.println("错误2");
            throw new CustermizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                System.out.println("错误3");
                throw new CustermizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                System.out.println("错误4");
                throw new CustermizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);//增加一个评论数
        }
    }

    public List<CommentDTO> listByQuestionId(Long id) {
        //根据id去数据库查找
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        //如果查到的评论为空,直接返回
        if (comments.size() == 0) return new ArrayList<>();
        //取出所有的评论者,同时过滤掉回答了相同问题的评论者
        Set<Long> commentators  = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        //转换成list类型
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);


        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
