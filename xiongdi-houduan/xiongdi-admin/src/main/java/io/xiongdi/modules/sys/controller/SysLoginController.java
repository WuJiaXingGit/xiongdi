package io.xiongdi.modules.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.xiongdi.common.utils.R;
import io.xiongdi.common.validator.ValidatorUtils;
import io.xiongdi.modules.sys.entity.LoginForm;
import io.xiongdi.modules.sys.entity.SysUserEntity;
import io.xiongdi.modules.sys.service.SysUserService;
import io.xiongdi.modules.sys.service.TokenService;
import io.xiongdi.modules.sys.shiro.ShiroUtils;
import io.xiongdi.modules.sys.shiro.TokenGenerator;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录相关
 * @author wujiaxing
 * @date 2019-08-18
 */
@Controller
public class SysLoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TokenService tokenService;

    /**
     * 验证码制作类
     */
    @Autowired
    private Producer producer;

    /**
     * 验证码
     * @param response
     * @throws IOException
     */
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {
        // 设置响应头，不缓存、类型为图片
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片
        BufferedImage image = producer.createImage(text);
        // 将验证码存储到 shiro session
        ShiroUtils.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    /**
     * 登录
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public R login(@RequestBody  LoginForm loginForm) {
        System.out.println("dddddddddddd"+loginForm.getPassword());
        ValidatorUtils.validateEntity(loginForm);

        // 用户名和密码必须不为空
      if (StringUtils.isBlank(loginForm.getUsername()) || StringUtils.isBlank(loginForm.getPassword())) {
          return R.error(HttpStatus.SC_UNAUTHORIZED, "请输入用户名或密码");
      }

      // 用户信息
      SysUserEntity sysUserEntity = sysUserService.queryUser(loginForm.getUsername());
      // 账号不存在、密码错误
      if (sysUserEntity == null || !sysUserEntity.getPassword().equals(ShiroUtils.sha256(loginForm.getPassword(), sysUserEntity.getSalt()))) {
          return R.error(HttpStatus.SC_UNAUTHORIZED, "用户名或密码错误");
      }

      // 账号被锁定
     if (sysUserEntity.getStatus() == 0) {
        return R.error(HttpStatus.SC_UNAUTHORIZED, "账号被锁定");
     }

     return tokenService.createToken(sysUserEntity.getUserId());
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/sys/logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }

    public static void main(String[] args) {
        System.out.println(ShiroUtils.sha256("123456","YzcmCZNvbXocrsz9dm8e"));
    }
}
