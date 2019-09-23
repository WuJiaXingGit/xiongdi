package io.xiongdi.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色与菜单对应的关系实体类，这是一张第三方表
 * @author wujiaxing
 * @date 2019-08-08
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenuEntity implements Serializable {
    private static final long serialVersionUID = 4095583643390762831L;

    @TableId
    private Long id;

    /**
     *  角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
}
