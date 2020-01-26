package life.james.community.advice;
import life.james.community.dto.ResultDTO;
import life.james.community.exception.CustermizeException;
import life.james.community.exception.CustomizeErrorCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//用于处理错误跳转
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model, HttpServletRequest request,
                  HttpServletResponse response) {
        //有错就直接返回到error页面
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回json
            ResultDTO resultDTO;
            if (e instanceof CustermizeException) {
               return ResultDTO.errorOf((CustermizeException) e);
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
//            try{
//                response.setContentType("application/json");
//                response.setStatus(200);
//                response.setCharacterEncoding("utf-8");
//                PrintWriter writer = response.getWriter();
//                writer.write(JSON.toJSONString(resultDTO));
//                writer.close();
//            }catch(IOException ioe){
//
//            }
//            return null;
        }else{
            //错误页面跳转
            if (e instanceof CustermizeException) {
                model.addAttribute("message",e.getMessage());
            } else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
