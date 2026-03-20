package com.etshop.controller;

import com.etshop.component.RedisComponent;
import com.etshop.entity.config.AppConfig;
import com.etshop.entity.constants.Constants;
import com.etshop.entity.vo.CheckCodeVO;
import com.wf.captcha.ArithmeticCaptcha;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {
    @Resource
    private RedisComponent redisComponent;

    @Resource
    private AppConfig appConfig;

    @RequestMapping("/checkCode")
    public CheckCodeVO checkCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);
        String code = captcha.text();
        String checkCodeBase64 = captcha.toBase64();
        String checkCodeKey = redisComponent.saveCheckCode(code);
        CheckCodeVO checkCodeVO = new CheckCodeVO(checkCodeBase64, checkCodeKey);
        return checkCodeVO;
    }

    @PostMapping("/login")
    public String login(@NotEmpty String account, @NotEmpty String password,
                        @NotEmpty String checkCodeKey, @NotEmpty String checkCode) {
       try {
           if (!checkCode.equalsIgnoreCase(redisComponent.getCheckCode(checkCodeKey))) {
               return "验证码错误";
           }
           if (!account.equalsIgnoreCase(appConfig.getAdminAccount()) || !password.equals(appConfig.getAdminPassword())) {
               return "账号或密码错误";
           }
           String token = redisComponent.saveTokenInfo4Admin(account);
           return token;
       } finally {
           redisComponent.deleteCheckCode(checkCodeKey);
       }
    }

}