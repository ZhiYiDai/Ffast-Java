package cn.ffast.core.constant;

/**
 * @author daizhiyi
 * @version 1.0
 * @Description: 结果返回code
 * @date 2017年3月24日
 */
public enum ResultCode {
    /**
     * 成功返回
     */
    SUCCESS(200, "成功"),
    /**
     * 未登录系统
     */
    NOTLOGIN(1000, "未登录系统！"),
    /**
     * 操作失败
     */
    ERROR(1001, "操作失败！"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(1002, "权限不足"),
    /**
     * 不存在
     */
    INEXISTENCE(1003, "不存在"),
    /**
     * 存在
     */
    EXIST(1004, "存在"),
    /**
     * 参数为空
     */
    PARAMNULL(1005, "参数为空"),
    /**
     * 服务内部错误
     */
    SERVICE_ERROR(500, "服务内部错误");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

 