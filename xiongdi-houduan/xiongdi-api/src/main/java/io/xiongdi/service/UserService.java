package io.xiongdi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.entity.UserEntity;
import io.xiongdi.form.LoginForm;
import java.util.Map;

/**
 *  用户
 * @author wujiaxing
 * @date 2019-06-30
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 根据手机号查询用户
     * @param mobile 手机号
     * @return 返回用户信息
     */
    UserEntity queryByMobile(String mobile);

    /**
     * 登录
     * @param form 登录表单
     * @return 返回登录信息
     */
    Map<String, Object> login(LoginForm form);

}
