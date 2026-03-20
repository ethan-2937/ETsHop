package com.etshop.component;


import com.etshop.entity.constants.Constants;
import com.etshop.redis.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class RedisComponent {
    @Resource
    private RedisUtils redisUtils;
    public String saveCheckCode(String checkCode)  {
        String key = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_CHECK_CODE + key, checkCode, 60*10);
        return key;
    }

    public String getCheckCode(String key) {
        return (String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + key);
    }

    public String saveTokenInfo4Admin(String account) {
        String token = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_TOKEN_ADMIN + token, account, Constants.REDIS_KEY_EXPIRE_DAY);
        return token;
    }

    public void deleteCheckCode(String key) {
        redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + key);
    }
}
