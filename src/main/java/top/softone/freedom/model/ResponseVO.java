package top.softone.freedom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月07日 13:10
 * @since JDK 1.8
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseVO<T> {
    private static final String SUCCESS_CODE;

    private String code;

    private boolean isSuccess;

    private String msg;

    private T data;

    static {
        SUCCESS_CODE = "0";
    }

    public Boolean getIsSuccess() {
        return SUCCESS_CODE.equals(this.code);
    }

    public static <T> ResponseVO<T> success() {
        return success(null);
    }

    public static <T> ResponseVO<T> success(T data) {
        ResponseVO<T> response = new ResponseVO();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        response.setMsg("Success");
        return response;
    }

    public static <T> ResponseVO<T> failure(String statusCode, String message) {
        ResponseVO<T> response = new ResponseVO();
        response.setCode(statusCode);
        response.setMsg(message);
        return response;
    }

    public static <T> ResponseVO<T> failure(String statusCode) {
        ResponseVO<T> response = new ResponseVO();
        response.setCode(statusCode);
        response.setMsg("失败");
        return response;
    }
}
