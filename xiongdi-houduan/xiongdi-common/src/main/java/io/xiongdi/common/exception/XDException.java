package io.xiongdi.common.exception;

import io.xiongdi.common.utils.ResultType;

/**
 * 自定义异常
 * @author wujiaxing
 */
public class XDException extends RuntimeException{
    /**
     * 错误消息
     */
    private String msg;
    /**
     * 错误代码
     */
    private int code = 500;

    public XDException(ResultType resultType){
        super(resultType.msg());
        this.msg = resultType.msg();
        this.code = resultType.code();
    }

    public XDException(ResultType resultType, Throwable e) {
        super(resultType.msg(), e);
        this.msg = resultType.msg();
        this.code = resultType.code();
    }

    public XDException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public XDException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public XDException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public XDException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
