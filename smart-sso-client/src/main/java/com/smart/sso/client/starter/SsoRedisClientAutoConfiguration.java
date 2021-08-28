package com.smart.sso.client.starter;


import com.smart.sso.client.local.constant.SsoConstant;
import com.smart.sso.client.local.listener.LogoutListener;
import com.smart.sso.client.local.provider.SsoClientProperties;
import com.smart.sso.client.local.session.SessionMappingStorage;
import com.smart.sso.client.redis.manager.RedisAccessTokenManager;
import com.smart.sso.client.redis.manager.RedisCodeManager;
import com.smart.sso.client.redis.manager.RedisRefreshTokenManager;
import com.smart.sso.client.redis.manager.RedisTicketGrantingTicketManager;
import com.smart.sso.client.redis.storage.RedisSessionMappingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.events.AbstractSessionEvent;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wency
 */
@Configuration
@EnableScheduling
@EnableRedisHttpSession
@EnableConfigurationProperties(SsoClientProperties.class)
@ConditionalOnProperty(name = SsoClientProperties.SESSION_MANAGER, havingValue = SsoConstant.SESSION_MANAGER_REDIS, matchIfMissing = true)
public class SsoRedisClientAutoConfiguration {

    private final SsoClientProperties properties;

    public SsoRedisClientAutoConfiguration(SsoClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RedisAccessTokenManager redisAccessTokenManager(StringRedisTemplate redisTemplate) {
        return new RedisAccessTokenManager(properties.getTimeout(), redisTemplate);
    }

    @Bean
    public RedisCodeManager redisCodeManager(StringRedisTemplate redisTemplate) {
        return new RedisCodeManager(redisTemplate);
    }

    @Bean
    public RedisRefreshTokenManager redisRefreshTokenManager(StringRedisTemplate redisTemplate) {
        return new RedisRefreshTokenManager(properties.getTimeout(), redisTemplate);
    }

    @Bean
    public RedisTicketGrantingTicketManager redisTicketGrantingTicketManager(StringRedisTemplate redisTemplate) {
        return new RedisTicketGrantingTicketManager(properties.getTimeout(), redisTemplate);
    }

    /**
     * 分布式redis方式注册单点登出Listener
     * <p>
     * 注：
     * 1.需注入RedisSessionMappingStorage
     * 2.需要使用Spring方式注入LogoutListener，使用ServletListenerRegistrationBean方式不生效
     */
    @Autowired
    private SessionMappingStorage sessionMappingStorage;

    @Bean
    public SessionMappingStorage sessionMappingStorage(StringRedisTemplate redisTemplate, SessionRepository<?> sessionRepository) {
        return new RedisSessionMappingStorage(redisTemplate, sessionRepository);
    }

    @Bean
    public ApplicationListener<AbstractSessionEvent> LogoutListener() {
        List<HttpSessionListener> httpSessionListeners = new ArrayList<>();
        LogoutListener logoutListener = new LogoutListener();
        logoutListener.setSessionMappingStorage(sessionMappingStorage);
        httpSessionListeners.add(logoutListener);
        return new SessionEventHttpSessionListenerAdapter(httpSessionListeners);
    }
}