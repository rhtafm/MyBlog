package wiki.jixing.myblog.exception;

import org.springframework.stereotype.Component;
import wiki.jixing.myblog.enums.ErrorEnum;

@Component
public class CustomException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String message;

    public CustomException() {
        super();
    }

    public CustomException(ErrorEnum error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
