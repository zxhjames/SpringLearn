package life.james.community.exception;

//枚举通用的状态异常码
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你的问题不在了,请换一个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题进行评论"),
    NO_LOGIN(2003,"当前操作需要登录,请登录后重试"),
    SYS_ERROR(2004,"服务器错误"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你找的评论不存在了,要不换个试试?");
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;
    CustomizeErrorCode(Integer code,String message)
    {
        this.message = message;
        this.code = code;
    }

}
