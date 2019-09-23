package io.xiongdi.modules.sys.controller;

import io.xiongdi.common.utils.PageUtils;
import io.xiongdi.common.utils.R;
import io.xiongdi.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 系统日志
 * @author wujiaxing
 * @date 2019-08-15
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 日志列表
     * @param params key参数必须
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = this.sysLogService.queryPage(params);

        return R.ok().put("page", page);
    }
}
