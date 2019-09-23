package io.xiongdi.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.Query;
import io.xiongdi.modules.sys.dao.SysConfigDao;
import io.xiongdi.modules.sys.entity.SysConfigEntity;
import io.xiongdi.modules.sys.redis.SysConfigRedis;
import io.xiongdi.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 系统配置
 *
 * @author wujiaxing
 * @date 2019-08-10
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    @Autowired
    private SysConfigRedis sysConfigRedis;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 获取key
        String paramKey = (String) params.get("paramKey");
        // 从数据库查询
        IPage<SysConfigEntity> page = this.page(new Query<SysConfigEntity>().getPage(params),
                new QueryWrapper<SysConfigEntity>().like(StringUtils.isNotBlank(paramKey), "param_key", paramKey)
                        .eq("status", 1));
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveConfig(SysConfigEntity config) throws JsonProcessingException {
        this.save(config);
        sysConfigRedis.saveOrUpdate(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfigEntity config) throws JsonProcessingException {
        this.updateById(config);
        sysConfigRedis.saveOrUpdate(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
        sysConfigRedis.delete(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            SysConfigEntity configEntity = this.getById(id);
            sysConfigRedis.delete(configEntity.getParamKey());
        }
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public String getValue(String key) throws IOException {
        SysConfigEntity sysConfigEntity = sysConfigRedis.get(key);
        if (sysConfigEntity == null) {
            sysConfigEntity = baseMapper.queryByKey(key);
            sysConfigRedis.saveOrUpdate(sysConfigEntity);
        }
        return sysConfigEntity == null ? null : sysConfigEntity.getParamValue();
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) throws IOException {
        String value = getValue(key);
        if (StringUtils.isNotBlank(value)) {
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
           throw new XDException("获取参数失败");
        }
    }
}
