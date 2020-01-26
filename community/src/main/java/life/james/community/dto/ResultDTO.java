package life.james.community.dto;

import life.james.community.exception.CustermizeException;
import life.james.community.exception.CustomizeErrorCode;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

@Data
public class ResultDTO {
    private Integer code;
    private String message;
    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    //请求错误状态码
    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustermizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }
    //请求成功状态码

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
