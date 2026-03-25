package com.etshop.controller;

import com.etshop.component.RedisComponent;
import com.etshop.entity.config.AppConfig;
import com.etshop.entity.constants.Constants;
import com.etshop.entity.vo.CheckCodeVO;
import com.etshop.entity.vo.ResponseVO;
import com.etshop.exception.BusinessException;
import com.etshop.utils.StringTools;
import com.wf.captcha.ArithmeticCaptcha;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController extends ABaseController {
    @Resource
    private RedisComponent redisComponent;

    @Resource
    private AppConfig appConfig;

    @RequestMapping("/checkCode")
    public ResponseVO checkCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);
        String code = captcha.text();
        String checkCodeBase64 = captcha.toBase64();
        String checkCodeKey = redisComponent.saveCheckCode(code);
        CheckCodeVO checkCodeVO = new CheckCodeVO(checkCodeBase64, checkCodeKey);
        return getSuccessResponseVO(checkCodeVO);
    }

    @RequestMapping("/login")
    public ResponseVO login(@NotEmpty String account, @NotEmpty String password,
                        @NotEmpty String checkCodeKey, @NotEmpty String checkCode) {
       try {
           if (!checkCode.equalsIgnoreCase(redisComponent.getCheckCode(checkCodeKey))) {
               throw new BusinessException("验证码错误");
           }
           if (!account.equalsIgnoreCase(appConfig.getAdminAccount()) || !password.equalsIgnoreCase(StringTools.encodeByMD5(appConfig.getAdminPassword()))) {
               throw new BusinessException("账号或密码错误");
           }
           String token = redisComponent.saveTokenInfo4Admin(account);
           return getSuccessResponseVO(token);
       } finally {
           redisComponent.deleteCheckCode(checkCodeKey);
       }
    }

    @RequestMapping("/logout")
    public ResponseVO logout(@RequestHeader(Constants.REDIS_KEY_TOKEN_ADMIN) String token) {
        redisComponent.deleteTokenInfo4Admin(token);
        return getSuccessResponseVO(null);
    }
}