package io.xiongdi.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.utils.Constant;
import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.Query;
import io.xiongdi.modules.sys.dao.SysUserDao;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import io.xiongdi.modules.sys.service.SysUserRoleService;
import io.xiongdi.modules.sys.service.SysUserService;
import io.xiongdi.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @author wujiaxing
 * @date 2019-08-10
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .like(StringUtils.isNotBlank(username), "username", username)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUserEntity user) {
        user.setCreateTime(DateUtils.now());
        // 生成盐，20位由字母和数字组合的字符串
        String salt = RandomStringUtils.randomAlphanumeric(20);
        // 保存盐，每个用户的盐是不一样的
        user.setSalt(salt);
        // 保存使用sha256加密的密码
        user.setPassword(ShiroUtils.sha256(user.getPassword(), salt));
        this.save(user);
        // 保存用户和角色的关系
        sysUserRoleService.saveOrUpdate(user.getUserId(),user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserEntity user) {
        // 如果用户密码为空，则为原密码
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            SysUserEntity userEntity = this.getById(user.getUserId());
            // 设置新密码，使用原来的盐
            user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
        }
        // 根据ID修改用户信息
        this.updateById(user);
        // 修改用户与角色对应关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(Long userId, String password, String newPassword) {
        // 设置需要修改的列，属性为 null 的列是不会修改的
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setPassword(newPassword);
        return this.update(sysUserEntity,
                new UpdateWrapper<SysUserEntity>()
                    .eq("user_id", userId)
                    .eq("password", password));
    }

    @Override
    public SysUserEntity queryUser(String username) {
        return this.getOne(new QueryWrapper<SysUserEntity>().eq(StringUtils.isNotBlank(username), "username", username));
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return this.getById(userId);
    }
}
