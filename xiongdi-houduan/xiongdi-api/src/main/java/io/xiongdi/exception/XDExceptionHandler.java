package io.xiongdi.exception;

import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.utils.ResultType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常统一处理器
 * @author wujiaxing
 */
@RestControllerAdvice
public class XDExceptionHandler {

    /**
     * 自定义异常
     * @param e 拦截到的自定义异常类 XDException
     * @return
     */
    @ExceptionHandler(XDException.class)
    public R handleXDException(XDException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMsg());
        return r;
    }

    /**
     * 数据库存在异常
     * @param e 异常类型 DuplicateKeyException
     * @return
     */
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        R r = new R(ResultType.DATABASE_EXIST_KEY);
        return r;
    }
    /**
     * 总异常
     * @param e 异常类型 Exception
     * @return
     */
    public R handleException(Exception e) {
        R r = new R(ResultType.SERVER_INNER_EXCEPTION);
        return r;
    }
}
