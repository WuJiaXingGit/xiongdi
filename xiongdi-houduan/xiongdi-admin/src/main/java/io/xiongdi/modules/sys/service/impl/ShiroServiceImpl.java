package io.xiongdi.modules.sys.service.impl;

import io.xiongdi.common.utils.Constant;
import io.xiongdi.modules.sys.dao.SysMenuDao;
import io.xiongdi.modules.sys.dao.SysUserDao;
import io.xiongdi.modules.sys.entity.SysMenuEntity;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import io.xiongdi.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public Set<String> getUserPermissions(long userId) {

        List<String> permsList;

        // 如果是系统管理员，则拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }

        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        return permsSet;
    }

}
