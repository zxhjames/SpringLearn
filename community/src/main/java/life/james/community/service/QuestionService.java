package life.james.community.service;

import life.james.community.dto.PaginationDTO;
import life.james.community.dto.QuestionDTO;
import life.james.community.mapper.QuestionMapper;
import life.james.community.mapper.UserMapper;
import life.james.community.model.Question;
import life.james.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties(p,d);
            //p是等待被赋值的对象,d是源对象,将d中属性值赋值的p中对应的字段,d中有的属性p中必须有,p可以有更多属性
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);//最后加上多出的user
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userid, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByUserId(userid,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties(p,d);
            //p是等待被赋值的对象,d是源对象,将d中属性值赋值的p中对应的字段,d中有的属性p中必须有,p可以有更多属性
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);//最后加上多出的user
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount = questionMapper.countByUserId(userid);
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }
}
