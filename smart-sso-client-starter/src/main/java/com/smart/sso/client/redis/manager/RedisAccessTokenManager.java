package com.smart.sso.client.redis.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.sso.client.common.AccessTokenContent;
import com.smart.sso.client.local.session.AccessTokenManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 分布式调用凭证管理
 * 
 * @author Joe
 */
public class RedisAccessTokenManager implements AccessTokenManager {
	
    private int timeout;

	private StringRedisTemplate redisTemplate;

	public RedisAccessTokenManager(int timeout, StringRedisTemplate redisTemplate) {
		this.timeout = timeout;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void create(String accessToken, AccessTokenContent accessTokenContent) {
		redisTemplate.opsForValue().set(accessToken, JSON.toJSONString(accessTokenContent), getExpiresIn(),
				TimeUnit.SECONDS);

		redisTemplate.opsForSet().add(getKey(accessTokenContent.getCodeContent().getTgt()), accessToken);
	}
	
	@Override
	public AccessTokenContent get(String accessToken) {
		String atcStr = redisTemplate.opsForValue().get(accessToken);
		if (StringUtils.isEmpty(atcStr)) {
			return null;
		}
		return JSONObject.parseObject(atcStr, AccessTokenContent.class);
	}

	@Override
	public boolean refresh(String accessToken) {
		if (redisTemplate.opsForValue().get(accessToken) == null) {
			return false;
		}
		redisTemplate.expire(accessToken, timeout, TimeUnit.SECONDS);
        return true;
	}

	@Override
	public void remove(String tgt) {
		Set<String> accessTokenSet = redisTemplate.opsForSet().members(getKey(tgt));
		if (CollectionUtils.isEmpty(accessTokenSet)) {
			return;
		}
		redisTemplate.delete(getKey(tgt));
		
		accessTokenSet.forEach(accessToken -> {
			String atcStr = redisTemplate.opsForValue().get(accessToken);
			if (StringUtils.isEmpty(atcStr)) {
				return;
			}
			AccessTokenContent accessTokenContent = JSONObject.parseObject(atcStr, AccessTokenContent.class);
			if (accessTokenContent == null || !accessTokenContent.getCodeContent().isSendLogoutRequest()) {
				return;
			}
			sendLogoutRequest(accessTokenContent.getCodeContent().getRedirectUri(), accessToken);
		});
	}
	
	private String getKey(String tgt) {
		return tgt + "_access_token";
	}
	
	/**
	 * accessToken时效为登录session时效的1/2
	 */
	@Override
	public int getExpiresIn() {
		return timeout / 2;
	}
}
