package com.smart.sso.demo.config;

import com.smart.sso.client.local.SmartContainer;
import com.smart.sso.client.local.filter.AppLoginFilter;
import com.smart.sso.client.local.filter.AppLogoutFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SmartSsoConfig {

	@Value("${smart.sso.server-url}")
	private String serverUrl;
	@Value("${smart.sso.app-id}")
	private String appId;
	@Value("${smart.sso.app-secret}")
	private String appSecret;


    /**
     * App登录Filter容器（可与Web方式共存）
     *
     * @return
     */
	@Bean
	public FilterRegistrationBean<SmartContainer> appSmartContainer() {
		SmartContainer smartContainer = new SmartContainer();
		smartContainer.setServerUrl(serverUrl);
		smartContainer.setAppId(appId);
		smartContainer.setAppSecret(appSecret);

		smartContainer.setExcludeUrls("/app/login");

		smartContainer.setFilters(new AppLogoutFilter(), new AppLoginFilter());

		FilterRegistrationBean<SmartContainer> registration = new FilterRegistrationBean<>();
		registration.setFilter(smartContainer);
		registration.addUrlPatterns("/app/*");
		registration.setOrder(2);
		registration.setName("appSmartContainer");
		return registration;
	}
}
