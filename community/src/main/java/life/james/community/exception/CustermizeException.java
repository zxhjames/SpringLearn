package life.james.community.exception;

public class CustermizeException  extends RuntimeException{
    private String message;
    private Integer code;
    public CustermizeException(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    public CustermizeException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
