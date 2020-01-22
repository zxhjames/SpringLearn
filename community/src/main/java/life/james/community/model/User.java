package life.james.community.model;

import lombok.Data;
@Data //自动生产get set方法
public class User {
    private Integer id;
    private String name;
    private  String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
