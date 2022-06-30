package wiki.jixing.myblog.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wiki.jixing.myblog.enums.ErrorEnum;
import wiki.jixing.myblog.utils.Result;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
        ErrorEnum error;
        if (e instanceof CustomException) {
            CustomException ce = (CustomException) e;
            error = ErrorEnum.getByCode(ce.getCode());
        } else {
            error = ErrorEnum.UNKNOWN_ERROR;
        }
        logger.info("Error : {}", e.getMessage());
        Result result = new Result(error.getCode(), error.getMessage(), null);
        model.addAttribute("result", result);
        return "error/error";
    }
}
