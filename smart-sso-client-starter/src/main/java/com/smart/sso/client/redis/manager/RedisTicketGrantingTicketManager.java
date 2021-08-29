package com.smart.sso.client.redis.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.sso.client.local.rpc.SsoUser;
import com.smart.sso.client.local.session.TicketGrantingTicketManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 分布式登录凭证管理
 * 
 * @author Joe
 */
public class RedisTicketGrantingTicketManager implements TicketGrantingTicketManager {

    private int timeout;

	private StringRedisTemplate redisTemplate;

	public RedisTicketGrantingTicketManager(int timeout, StringRedisTemplate redisTemplate) {
		this.timeout = timeout;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void create(String tgt, SsoUser user) {
		redisTemplate.opsForValue().set(tgt, JSON.toJSONString(user), getExpiresIn(),
				TimeUnit.SECONDS);
	}

	@Override
	public SsoUser getAndRefresh(String tgt) {
		String user = redisTemplate.opsForValue().get(tgt);
		if (StringUtils.isEmpty(user)) {
			return null;
		}
		redisTemplate.expire(tgt, timeout, TimeUnit.SECONDS);
		return JSONObject.parseObject(user, SsoUser.class);
	}
	
	@Override
	public void set(String tgt, SsoUser user) {
		create(tgt, user);
	}

	@Override
	public void remove(String tgt) {
		redisTemplate.delete(tgt);
	}

	@Override
	public int getExpiresIn() {
		return timeout;
	}
}