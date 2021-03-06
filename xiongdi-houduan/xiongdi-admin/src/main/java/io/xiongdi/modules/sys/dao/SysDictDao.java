package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysDictEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity> {
}
