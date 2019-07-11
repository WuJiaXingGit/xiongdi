package io.xiongdi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xiongdi.annotation.Login;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.form.LoginForm;
import io.xiongdi.service.TokenService;
import io.xiongdi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author wujiaxing
 * @date 2019-07-07
 */
@Api(tags = "登录接口")
@RequestMapping("/api")
@RestController
public class ApiLoginController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody  LoginForm loginForm) {
        // 服务端表单校验
        System.out.println("已进入login"+loginForm);
        ValidatorUtils.validateEntity(loginForm);
        // 执行登录
        Map<String, Object> map = userService.login(loginForm);

        return R.ok(map);
    }

    /**
     * <p>
     *     登出需要请求中带token
     *     @RequestAttribute 这个注解表示访问有过滤器或拦截器创建的、预先存在的属性
     * </p>
     * @param userId
     * @return
     */
    @Login
    @ApiOperation("登出")
    @RequestMapping("logout")
    public R logout(@RequestAttribute("userId") @ApiIgnore long userId) {
        tokenService.expireToken(userId);
        return R.ok();
    }
}
