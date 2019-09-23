package io.xiongdi.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.Query;
import io.xiongdi.modules.oss.dao.SysOssDao;
import io.xiongdi.modules.oss.entity.SysOssEntity;
import io.xiongdi.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文件上传 service 实现类
 * @author wujiaxing
 * @date 2019-08-06
 */
@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOssEntity> page = this.page(new Query<SysOssEntity>().getPage(params));
        return new PageUtils(page);
    }
}
