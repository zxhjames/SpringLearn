package life.james.community.service;

import life.james.community.dto.PaginationDTO;
import life.james.community.dto.QuestionDTO;
import life.james.community.mapper.QuestionMapper;
import life.james.community.mapper.UserMapper;
import life.james.community.model.Question;
import life.james.community.model.QuestionExample;
import life.james.community.model.User;
import org.apache.ibatis.session.RowBounds;
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

    //用于主页显示所有问题分页
    public PaginationDTO list(Integer page, Integer size) {
        Integer totalPage;
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        if(page<1){
            page = 1;
        }
        if(page>totalPage){
            page = totalPage;
        }
        Integer offset = size * (page - 1);
        //重构实现分页,按照偏移量查询
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),
                new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {

            //重构:根据id查找findById
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties(p,d);
            //p是等待被赋值的对象,d是源对象,将d中属性值赋值的p中对应的字段,d中有的属性p中必须有,p可以有更多属性
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);//最后加上多出的user
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);

        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }



    //用于分页显示我的所有问题
    public PaginationDTO listByUserId(Integer userid, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userid);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        if(page<1){
            page = 1;
        }
        if(page>totalPage){
            page = totalPage;
        }


        Integer offset = size * (page - 1);
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria().andCreatorEqualTo(userid);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample1,
                new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties(p,d);
            //p是等待被赋值的对象,d是源对象,将d中属性值赋值的p中对应的字段,d中有的属性p中必须有,p可以有更多属性
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);//最后加上多出的user
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalCount, page, size);
        return paginationDTO;
    }




    //用于根据
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void CreateOrUpdate(Question question) {
        if(question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);//重写create方法
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion,example);//重写update方法
        }
    }

}
