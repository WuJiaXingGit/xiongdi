package io.xiongdi.modules.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.utils.Constant;
import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.Query;
import io.xiongdi.modules.sys.dao.SysRoleDao;
import io.xiongdi.modules.sys.entity.SysRoleEntity;
import io.xiongdi.modules.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

/**
 * 角色管理
 * @author wujiaxing
 * @date 2019-08-11
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 拥有子部门权限，才能查询，就算查询本人数据，也需要本部门权限
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        IPage<SysRoleEntity> page = this.page(new Query<SysRoleEntity>().getPage(params),
                new QueryWrapper<SysRoleEntity>()
                        .like(StringUtils.isNotBlank(roleName), "role_name", roleName)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savaRole(SysRoleEntity role) {
        role.setCreateTime(DateUtils.now());
        this.save(role);
        // 保存角色与菜单关系
        System.out.println("JSON="+ JSON.toJSONString(role.getMenuIdList()));
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);
        // 修改角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        // 删除角色
        this.removeByIds(Arrays.asList(roleIds));
        // 删除角色与菜单关系
        sysRoleMenuService.deleteBatch(roleIds);
        // 删除角色与用户关系
        sysUserRoleService.deleteBatch(roleIds);
    }
}
