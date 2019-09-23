package io.xiongdi.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.Query;
import io.xiongdi.modules.sys.dao.SysDictDao;
import io.xiongdi.modules.sys.entity.SysDictEntity;
import io.xiongdi.modules.sys.service.SysDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 数据字典
 * @author wujiaxing
 * @date 2019-08-10
 */
@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        IPage<SysDictEntity> page = this.page(new Query<SysDictEntity>().getPage(params),
                new QueryWrapper<SysDictEntity>().like(StringUtils.isNotBlank(name), "name", name));
        return new PageUtils(page);
    }
}
