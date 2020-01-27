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
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
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
            System.out.println("错误1");
            throw new CustermizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            System.out.println("错误2");
            throw new CustermizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbcomment==null){
                System.out.println("错误3");
                throw new CustermizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                System.out.println("错误4");
                throw new CustermizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);//增加一个评论数
        }
    }
}
