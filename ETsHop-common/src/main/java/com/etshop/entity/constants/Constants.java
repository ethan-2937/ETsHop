package com.etshop.entity.constants;

public class Constants {
    public static final String REDIS_KEY_PREFIX = "ETShop:";
    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "CheckCode:";
    public static final String REDIS_KEY_TOKEN_ADMIN = REDIS_KEY_PREFIX + "TokenAdmin:";
    public static final Long REDIS_KEY_EXPIRE_MINUTE = 60L;
    public static final Long REDIS_KEY_EXPIRE_DAY = REDIS_KEY_EXPIRE_MINUTE * 24;

}
