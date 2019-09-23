package io.xiongdi.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典实体类
 * @author wujiaxing
 * @date 2019-08-07
 */
@Data
@TableName("sys_dict")
public class SysDictEntity implements Serializable {

    private static final long serialVersionUID = -6348479918285410229L;

    @TableId
    private Long id;
    /**
     *  字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;
    /**
     *  字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String type;
    /**
     *  字典码
     */
    @NotBlank(message = "字典码不能为空")
    private String code;
    /**
     *  字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String value;
    /**
     *  排序
     */
    private Integer orderNum;
    /**
     *  备注
     */
    private String remark;
    /**
     *  删除标志  -1：已删除   0：正常
     *  使用此注解表示逻辑删除（就是执行删除语句时，会变成修改语句）
     */
    @TableLogic
    private Integer deFlag;
}
