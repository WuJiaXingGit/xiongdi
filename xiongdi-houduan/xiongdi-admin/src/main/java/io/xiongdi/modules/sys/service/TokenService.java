package io.xiongdi.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xiongdi.common.utils.R;
import io.xiongdi.modules.sys.entity.TokenEntity;

/**
 * token
 * @author wujiaxing
 * @date 2019-06-30
 */
public interface TokenService extends IService<TokenEntity> {

    /**
     * <p>
     *     根据请求token查询token信息
     * </p>
     * @param token
     * @return
     */
    TokenEntity queryByToken(String token);

    /**
     *  创建token
     * @param userId 用户ID
     * @return 返回token信息
     */
    R createToken(long userId);

    /**
     * 设置token过期
     * @param userId 用户ID
     */
    void expireToken(long userId);
}
