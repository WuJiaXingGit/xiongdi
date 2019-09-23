package io.xiongdi.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传 Dao
 * @author wujiaxing
 * @date 2019-08-06
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
}
