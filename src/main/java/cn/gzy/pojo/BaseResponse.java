package cn.gzy.pojo;


public class BaseResponse {
    private String message;
    private int code;
    private Object data;

    // Constructor
    public BaseResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Static method to return success response
    public static BaseResponse success(String message) {
        return new BaseResponse(200, message, null);
    }

    // Static method to return error response
    public static BaseResponse error(String message) {
        return new BaseResponse(500, message, null);
    }

    // Getter and Setter methods
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
