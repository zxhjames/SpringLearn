package life.james.community.mapper;

import life.james.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    //第一次登录
    @Insert("insert into user(name,accountId,token,gmt_create,gmt_modified,avatar_url) values" +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    //根据cookie中的token返回一个参数
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    //
    @Select("select * from user where id = #{creator}")
    User findById(@Param("creator") Integer creator);

    @Select("select * from user where accountId = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name},token = #{token},gmt_modified=#{gmtModified}" +
            ",avatar_url=#{avatarUrl} where id = #{id}")
    void update(User dbUser);

    /**
     * 考虑要不要使用flyway,解决数据库的更新问题
     */
}
