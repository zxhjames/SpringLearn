package life.james.community.dto;

import life.james.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    //添加一个user
    private String content;
    private User user;
    private Long id;
    private Long parentId;
    private Long type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
}
