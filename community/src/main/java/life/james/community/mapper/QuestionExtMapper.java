package life.james.community.mapper;

import life.james.community.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface QuestionExtMapper {
    int incView(Question record);
}