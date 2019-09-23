package io.xiongdi.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.Query;
import io.xiongdi.modules.sys.dao.SysLogDao;
import io.xiongdi.modules.sys.entity.SysLogEntity;
import io.xiongdi.modules.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 日志管理
 * @author wujiaxing
 * @date 2019-08-10
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        IPage<SysLogEntity> page = this.page(new Query<SysLogEntity>().getPage(params),
                new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key), "username", key));
        return new PageUtils(page);
    }
}
