package io.xiongdi.modules.sys.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "请输入密码")
    @Size(min = 6,message = "密码最小不能小于6位")
    private String password;
}
