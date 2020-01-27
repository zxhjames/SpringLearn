package life.james.community.dto;

import lombok.Data;

/**
 * 用于传输json类型的评论时使用的DTO
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Long type;
}
