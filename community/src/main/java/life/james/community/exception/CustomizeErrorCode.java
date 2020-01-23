package life.james.community.exception;

//枚举通用的状态异常码
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你的问题不在了,请换一个试试");

    @Override
    public String getMessage() {
        return message;
    }
    private String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }
}
