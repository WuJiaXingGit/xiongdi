package io.xiongdi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * DAO层【继承BaseMapper就具有了一些对UserEntity的基本操作方法】
 * @author wujiaxing
 * @date 2019-06-30
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
}
