package pers.fairy.miusa.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/4 15:13
 */
@Getter
public class Result {
    private int code;
    private String msg;
    private Object data;

    public static Result SUCCESS(Object data) {
        return new Result(0, "success", data);
    }

    public static Result ERROR(String msg) {
        return new Result(501, "error:" + msg);
    }

    public static Result SERVER_ERROR() {
        return new Result(500, "server_error");
    }

    public static Result ORDER_ERROR(String msg) {
        return new Result(510, "order_error:" + msg);
    }

    public static Result GOOD_ERROR(String msg) {
        return new Result(520, "good_error" + msg);
    }

    public static Result LOGIN_ERROR(String msg) {
        return new Result(530, "login_error:" + msg);
    }

    public static Result ACCESS_ERROR(String msg) {
        return new Result(540, "access_error:" + msg);
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
