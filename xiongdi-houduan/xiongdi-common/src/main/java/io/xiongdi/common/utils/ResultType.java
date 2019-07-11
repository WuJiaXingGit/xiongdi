package io.xiongdi.common.utils;

/**
 * <p>枚举定义了一些常用的输出类型值</p>
 * @author wujiaxing
 */
public enum ResultType {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 未找到可用资源
     */
    NOT_FOUND_RESOURCE(404, "未找到可用资源"),
    /**
     * 服务器内部异常
     */
    SERVER_INNER_EXCEPTION(500, "服务器内部异常"),
    /**
     * 数据库存在记录
     */
    DATABASE_EXIST_KEY(501, "数据库存在记录"),
    /**
     * 缺少参数
     */
    MISSING_PARAM(901, "缺少参数"),
    /**
     * 逻辑错误
     */
    INNER_ERROR(902, "逻辑错误"),
    /**
     * 未知错误
     */
    UNKNOW_ERROR(999, "未知错误"),
    /**
     * token校验错误
     */
    TOKEN_ERROR(101, "token校验错误"),
    /**
     * 用户编码丢失
     */
    USER_ID_LOST(102, "用户编码丢失"),
    /**
     * 注册失败
     */
    REGISTER_FAILED(103, "注册失败"),
    /**
     * 注册成功
     */
    REGISTER_SUCCESS(203, "注册成功"),
    /**
     * 未找到用户
     */
    NOT_FOUND_USER(104, "未找到用户"),
    /**
     * 验证失败
     */
    VERTIFY_ERROR(105, "验证失败"),
    /**
     * 手机号重复
     */
    PHONENUM_DUP(106, "手机号重复"),
    /**
     * 手机号已经注册过
     */
    PHONENUM_ALREAD_REGISTER(107, "手机号已经注册过"),
    /**
     * token 不能为空
     */
    TOKEN_NULL(108, "token不能为空"),
    /**
     * token已过期
     */
    TOKEN_EXPIRE(109, "token已过期"),
    /**
     * 包含非法字符
     */
    INCLUTE_ILLEGALITY_PARAM(121, "包含非法参数");

    private int code;
    private String msg;

    /**
     * 信息代码
     * @return
     */
    public int code() {
        return code;
    }

    /**
     * 处理消息
     * @return
     */
    public String msg() {
        return msg;
    }

    ResultType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
