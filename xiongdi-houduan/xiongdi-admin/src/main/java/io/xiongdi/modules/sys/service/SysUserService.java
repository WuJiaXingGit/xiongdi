package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @author wujiaxing
 * @date 2019-08-10
 */
public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 查询分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户下的所有菜单
     * @param userId 用户ID
     * @return
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 保存用户
     * @param user
     */
    void saveUser(SysUserEntity user);

    /**
     * 修改用户
     * @param user
     */
    void update(SysUserEntity user);

    /**
     * 修改密码
     * @param userId  用户ID
     * @param password 原密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    /**
     * 查询用户
     * @param username
     * @return
     */
    SysUserEntity queryUser(String username);

    /**
     * 查询用户
     * @param userId
     * @return
     */
    SysUserEntity queryUser(Long userId);
}
