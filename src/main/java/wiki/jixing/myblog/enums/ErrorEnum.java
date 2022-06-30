package wiki.jixing.myblog.enums;

/**
 * 可能出现的错误
 */
public enum ErrorEnum {

    /**
     * HTTP错误信息
     */
    UNAUTHORIZED(401, "没有权限"),
    PAGE_NOT_FOUND(404, "页面不存在"),
    THE_SYSTEM_BUSY(500, "服务器繁忙"),

    /**
     * 自定义错误 6xx
     */
    UNKNOWN_ERROR(600, "未知错误");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 提示信息
     */
    private final String message;

    ErrorEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public static ErrorEnum getByCode(Integer code) {
        if (code != null) {
            for (ErrorEnum error : ErrorEnum.values()) {
                if (error.getCode().equals(code)) {
                    return error;
                }
            }
        }
        return null;
    }
}
