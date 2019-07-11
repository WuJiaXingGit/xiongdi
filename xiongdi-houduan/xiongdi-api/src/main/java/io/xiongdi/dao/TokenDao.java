package io.xiongdi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * TokenEntity 的操作DAO层【继承Base Mapper就具有了对TokenEntity的基本操作方法】
 * @author wujiaxing
 * @date 2019-06-30
 */
@Mapper
public interface TokenDao extends BaseMapper<TokenEntity> {
}
