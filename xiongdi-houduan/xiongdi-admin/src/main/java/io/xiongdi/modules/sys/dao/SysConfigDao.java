package io.xiongdi.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.xiongdi.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 * @author wujiaxing
 * @date 2019-08-10
 */
@Mapper
public interface SysConfigDao extends BaseMapper<SysConfigEntity> {

    /**
     * 根据 key,查询 value
     * @param paramKey key
     * @return 系统配置信息
     */
    SysConfigEntity queryByKey(@Param("paramKey") String paramKey);

    /**
     * 根据 key，更新 value
     * @param paramKey
     * @param paramValue
     * @return
     */
    int updateValueByKey(@Param("paramKey") String paramKey, @Param("paramValue") String paramValue);
}
