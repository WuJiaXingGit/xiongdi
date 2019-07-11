package io.xiongdi.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * @author wujiaxing
 */
@Data
@Builder
@TableName("tb_user")
public class UserEntity  implements Serializable {


    private static final long serialVersionUID = 1315432620351507739L;

    public UserEntity(){}

    public UserEntity(long userId, String username, String mobile, String password, LocalDateTime createTime) {
        this.userId = userId;
        this.username = username;
        this.mobile = mobile;
        this.password = password;
        this.createTime = createTime;
    }

    /**
     * 用户ID
     */
    @TableId
    private long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
