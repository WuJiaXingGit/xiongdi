package io.xiongdi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.utils.ResultType;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.entity.UserEntity;
import io.xiongdi.form.RegisterForm;
import io.xiongdi.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author wujiaxing
 * @date 2019-07-07
 * <p>
 *     注册接口
 * </p>
 */
@Api(tags = "注册接口")
@RestController
@RequestMapping("/api")
public class ApiRegisterController {

    @Autowired
    private UserService userService;

    @ApiOperation("注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterForm form) {
        // 表单校验
        ValidatorUtils.validateEntity(form);
        // 创建注册对象
        UserEntity userEntity = UserEntity
                .builder()
                .createTime(LocalDateTime.now())
                .mobile(form.getMobile())
                .username(form.getMobile())
                .password(DigestUtils.sha256Hex(form.getPassword()))
                .build();
        boolean isRegisterSuccess = this.userService.save(userEntity);
        // 注册成功
        if (isRegisterSuccess) {
           return R.ok(ResultType.REGISTER_FAILED);
        }
        // 注册失败，如数据库异常导致等
        return R.error(ResultType.REGISTER_FAILED);
    }

}
