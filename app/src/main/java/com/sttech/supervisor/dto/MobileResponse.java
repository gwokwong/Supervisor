package com.sttech.supervisor.dto;

public class MobileResponse<T> {

    public final static int CODE_OK = 1000;                        // 正常返回
    public final static int CODE_INVALID_SESSION = 2000;        // 登录超时
    public final static int CODE_LOGIC_EXCEPTION = 3000;        // 业务异常, 例如必填字段没填之类错误

    int code;
    String message;
    T data;

    public MobileResponse(int code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
