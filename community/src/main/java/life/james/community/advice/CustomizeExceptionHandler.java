package life.james.community.advice;

import life.james.community.exception.CustermizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//用于处理错误跳转
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {
        //有错就直接返回到error页面
        if (e instanceof CustermizeException) {
            model.addAttribute("message",e.getMessage());
        } else {
            model.addAttribute("message", "服务器冒烟了,请你等下试试!");
        }
        return new ModelAndView("error");
    }

//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
}
