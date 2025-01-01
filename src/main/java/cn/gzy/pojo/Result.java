package cn.gzy.pojo;

public class Result {
    private boolean success;
    private String message;
    private Object data;

    // 构造函数、getter和setter方法...

    // 静态方法用于创建成功的响应
    public static Result success(Object data) {
        Result result = new Result();
        result.success = true;
        result.data = data;
        return result;
    }

    // 静态方法用于创建失败的响应
    public static Result error(String message) {
        Result result = new Result();
        result.success = false;
        result.message = message;
        return result;
    }

    // Getter和Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
