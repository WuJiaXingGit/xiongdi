package io.xiongdi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xiongdi.annotation.Login;
import io.xiongdi.annotation.LoginUser;
import io.xiongdi.common.utils.R;
import io.xiongdi.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wujiaxing
 * @date 2019-07-07
 */
@Api(tags = "测试接口")
@RestController
@RequestMapping("/api")
public class ApiTestController {

    @Login
    @ApiOperation(value = "获取用户对象", response = UserEntity.class)
    @GetMapping("userInfo")
    public R userInfo(@LoginUser UserEntity userEntity) {
        return R.ok().put("user", userEntity);
    }

    @Login
    @ApiOperation("获取用户ID")
    @GetMapping("userId")
    public R userId(@RequestAttribute("userId") long userId) {
        return R.ok().put("userId", userId);
    }

    @ApiOperation("忽略token测试")
    @GetMapping("notToken")
    public R notToken() {
        return R.ok().put("msg", "无需token也能正常登录");
    }

}
