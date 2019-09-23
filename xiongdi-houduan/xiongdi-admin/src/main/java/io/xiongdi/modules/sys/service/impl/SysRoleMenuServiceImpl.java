package io.xiongdi.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.modules.sys.dao.SysRoleMenuDao;
import io.xiongdi.modules.sys.entity.SysRoleMenuEntity;
import io.xiongdi.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色与菜单的关系
 * @author wujiaxing
 * @date 2019-08-13
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        // 不管保存还是修改操作，都只变成保存，所有先移除角色与菜单关系
        deleteBatch(new Long[]{roleId});

        if (menuIdList == null || menuIdList.size() == 0) {
            return;
        }

        // 保存
        for (Long menuId : menuIdList) {
            SysRoleMenuEntity entity = new SysRoleMenuEntity();
            entity.setRoleId(roleId);
            entity.setMenuId(menuId);
            this.save(entity);
        }
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    @Override
    public int deleteBatch(Long[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }
}
