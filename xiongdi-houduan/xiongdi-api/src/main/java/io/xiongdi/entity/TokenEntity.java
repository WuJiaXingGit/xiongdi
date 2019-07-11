package io.xiongdi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户token
 * @author wujiaxing
 * @date ${2019-6-30}
 */
@Data
@TableName("tb_token")
@Builder
public class TokenEntity implements Serializable {

    private static final long serialVersionUID = 5584132314624077161L;

    public TokenEntity(){}

    public TokenEntity(long userId, String token, LocalDateTime expireTime, LocalDateTime updateTime) {
        this.userId = userId;
        this.token = token;
        this.expireTime = expireTime;
        this.updateTime = updateTime;
    }

    /**
     *  用户ID
     */
    @TableId(type = IdType.INPUT)
    private long userId;
    /**
     * token
     */
    private String token;
    /**
     *  过期时间
     */
    private LocalDateTime expireTime;
    /**
     *  修改时间
     */
    private LocalDateTime updateTime;
}
