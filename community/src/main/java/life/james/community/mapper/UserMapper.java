package life.james.community.mapper;

import life.james.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user(name,accountId,token,gmt_create,gmt_modified) values" +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    //根据cookie中的token返回一个参数
    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    /**
     * 考虑要不要使用flyway,解决数据库的更新问题
     */
}
