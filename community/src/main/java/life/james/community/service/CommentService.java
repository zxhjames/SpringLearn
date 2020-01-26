package life.james.community.service;


import life.james.community.enums.CommentTypeEnum;
import life.james.community.exception.CustermizeException;
import life.james.community.exception.CustomizeErrorCode;
import life.james.community.mapper.CommentMapper;
import life.james.community.mapper.QuestionExtMapper;
import life.james.community.mapper.QuestionMapper;
import life.james.community.model.Comment;
import life.james.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    //插入评论
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() ==0){
            throw new CustermizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustermizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbcomment==null){
                throw new CustermizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }else{
                commentMapper.insert(comment);
            }
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustermizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }else{
                commentMapper.insert(comment);
                questionExtMapper.incCommentCount(question);//增加一个评论数
            }
        }
    }
}
