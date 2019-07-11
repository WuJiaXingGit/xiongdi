package io.xiongdi.controller;

import io.xiongdi.common.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class ApiVueSysController {

    @RequestMapping("menu/nav")
    public R menuNav() {
        System.out.println("已进入请nav");
        return R.ok();
    }
}
