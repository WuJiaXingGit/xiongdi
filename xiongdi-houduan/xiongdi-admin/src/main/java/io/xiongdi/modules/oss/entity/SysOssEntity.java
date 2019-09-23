package io.xiongdi.modules.oss.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件上传实体类
 * @author wujiaxing
 * @date 2019-08-04
 */
@TableName("sys_oss")
@Data
public class SysOssEntity implements Serializable {

    private static final long serialVersionUID = 5691458815328651827L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * URL地址
     */
    private String url;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;
}
