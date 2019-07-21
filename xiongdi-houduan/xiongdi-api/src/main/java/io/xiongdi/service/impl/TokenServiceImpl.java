package io.xiongdi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.utils.DateUtils;
import io.xiongdi.dao.TokenDao;
import io.xiongdi.entity.TokenEntity;
import io.xiongdi.service.TokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * @author wujiaxing
 * @date 2019-07-08
 */
@Service("tokenService")
public class TokenServiceImpl extends ServiceImpl<TokenDao, TokenEntity> implements TokenService {

    /**
     * 12 小时过期 单位：毫秒
     */
    private final static int EXPIRE = 3600 * 12 * 1000;

    /**
     *  根据请求头的token查询数据库对应的token信息
     * @param token
     * @return
     */
    @Override
    public TokenEntity queryByToken(String token) {
        return this.getOne(new QueryWrapper<TokenEntity>().eq("token", token));
    }

    @Override
    public TokenEntity createToken(long userId) {
        // 得到当前时间
        LocalDateTime now = LocalDateTime.now();
        // 根据过期时间加上当前时间，得到token的有效期
        LocalDateTime tokenExpireDateTime = DateUtils.addDateMilliSecond(now, EXPIRE);
        // 生成token
        String token = generateToken();
        // 创建实体对象
        TokenEntity tokenEntity = TokenEntity.builder().expireTime(tokenExpireDateTime).userId(userId).token(token).updateTime(now).build();
        // 放入数据库保存
        this.saveOrUpdate(tokenEntity);
        return tokenEntity;
    }

    /**
     *  生成token
     * @return
     */
    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public void expireToken(long userId) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        TokenEntity tokenEntity = TokenEntity.builder().userId(userId).expireTime(now).updateTime(now).build();
        this.saveOrUpdate(tokenEntity);
    }
}
