package com.smart.sso.client.redis.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.sso.client.common.RefreshTokenContent;
import com.smart.sso.client.local.session.RefreshTokenManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 分布式刷新凭证管理
 * 
 * @author Joe
 */
public class RedisRefreshTokenManager implements RefreshTokenManager {

    private int timeout;

	private StringRedisTemplate redisTemplate;

	public RedisRefreshTokenManager(int timeout, StringRedisTemplate redisTemplate) {
		this.timeout = timeout;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void create(String refreshToken, RefreshTokenContent refreshTokenContent) {
		redisTemplate.opsForValue().set(refreshToken, JSON.toJSONString(refreshTokenContent), getExpiresIn(),
				TimeUnit.SECONDS);
	}

	@Override
	public RefreshTokenContent validate(String refreshToken) {
		String rtc = redisTemplate.opsForValue().get(refreshToken);
		if (!StringUtils.isEmpty(rtc)) {
			redisTemplate.delete(refreshToken);
		}
		return JSONObject.parseObject(rtc, RefreshTokenContent.class);
	}
	
	/*
	 * refreshToken时效和登录session时效一致
	 */
	@Override
	public int getExpiresIn() {
		return timeout;
	}
}
