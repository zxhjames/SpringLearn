package life.james.community.mapper;

import life.james.community.model.Question;
import life.james.community.model.QuestionExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
//用于扩展持久层方法(自定义实现),对应于questionExtMapper.xml
public interface QuestionExtMapper {
    int incView(Question record);//增加浏览数
    int incCommentCount(Question record);//增加评论数
    //按照时间顺序显示问题
    List<Question> selectByExampleByDateWithRowbounds(QuestionExample example, RowBounds rowBounds);
}