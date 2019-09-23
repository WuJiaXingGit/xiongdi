package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
}
