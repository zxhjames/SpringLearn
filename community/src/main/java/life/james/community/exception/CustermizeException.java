package life.james.community.exception;

public class CustermizeException  extends RuntimeException{
    public String message;

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
}
