package io.xiongdi.common.exception;


import io.xiongdi.common.utils.R;
import io.xiongdi.common.utils.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 * @author wujiaxing
 * @date 2019-07-24
 */
@Slf4j
@RestControllerAdvice
public class XDExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(XDException.class)
    public R handleXDException(XDException e){
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e){
        log.error(e.getMessage(), e);
        return R.error(ResultType.DATABASE_EXIST_KEY);
    }

    @ExceptionHandler(AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e){
        log.error(e.getMessage(), e);
        return R.error(ResultType.NOT_AUTHORIZATION);
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e){
        log.error(e.getMessage(), e);
        return R.error();
    }
}
