package io.xiongdi.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统配置实体类
 * @author wujiaxing
 * @date 2019-08-07
 */
@Data
@TableName("sys_config")
public class SysConfigEntity implements Serializable {
    private static final long serialVersionUID = 1835686205545125861L;

    /**
     *  配置ID
     */
    @TableId
    private Long id;

    /**
     * 参数名
     */
    @NotBlank(message = "参数名不能为空")
    private String paramKey;

    /**
     * 参数值
     */
    @NotBlank(message = "参数值不能为空")
    private String paramValue;

    /**
     * 备注
     */
    private String remark;
}
