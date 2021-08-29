package com.smart.sso.client.starter;

import com.smart.sso.client.local.listener.LogoutListener;
import com.smart.sso.client.local.manager.LocalAccessTokenManager;
import com.smart.sso.client.local.manager.LocalCodeManager;
import com.smart.sso.client.local.manager.LocalRefreshTokenManager;
import com.smart.sso.client.local.manager.LocalTicketGrantingTicketManager;
import com.smart.sso.client.provider.SsoClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionListener;

/**
 * @author wency
 */
@Configuration
@EnableConfigurationProperties(SsoClientProperties.class)
public class SsoClientAutoConfiguration {

    private final SsoClientProperties properties;

    public SsoClientAutoConfiguration(SsoClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public LocalRefreshTokenManager localRefreshTokenManager() {
        return new LocalRefreshTokenManager(properties.getTimeout());
    }

    @Bean
    public LocalAccessTokenManager localAccessTokenManager() {
        return new LocalAccessTokenManager(properties.getTimeout());
    }

    @Bean
    public LocalCodeManager localCodeManager() {
        return new LocalCodeManager();
    }

    @Bean
    public LocalTicketGrantingTicketManager localTicketGrantingTicketManager() {
        return new LocalTicketGrantingTicketManager(properties.getTimeout());
    }

    /**
     * 单实例方式注册单点登出Listener
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> LogoutListener() {
        ServletListenerRegistrationBean<HttpSessionListener> listenerRegBean = new ServletListenerRegistrationBean<>();
        LogoutListener logoutListener = new LogoutListener();
        listenerRegBean.setListener(logoutListener);
        return listenerRegBean;
    }


    /**
     * App登录Filter容器（可与Web方式共存）
     *
     * @return
     */
//	@Bean
//	public FilterRegistrationBean<SmartContainer> appSmartContainer() {
//		SmartContainer smartContainer = new SmartContainer();
//		smartContainer.setServerUrl(serverUrl);
//		smartContainer.setAppId(appId);
//		smartContainer.setAppSecret(appSecret);
//
//		smartContainer.setExcludeUrls("/app/login");
//
//		smartContainer.setFilters(new AppLogoutFilter(), new AppLoginFilter());
//
//		FilterRegistrationBean<SmartContainer> registration = new FilterRegistrationBean<>();
//		registration.setFilter(smartContainer);
//		registration.addUrlPatterns("/app/*");
//		registration.setOrder(2);
//		registration.setName("appSmartContainer");
//		return registration;
//	}
}
