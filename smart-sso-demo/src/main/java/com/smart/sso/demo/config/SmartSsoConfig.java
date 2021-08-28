//package com.smart.sso.demo.config;
//
//import javax.servlet.http.HttpSessionListener;
//
//import com.smart.sso.client.filter.AppLoginFilter;
//import com.smart.sso.client.filter.AppLogoutFilter;
//import com.smart.sso.client.session.SessionMappingStorage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.smart.sso.client.SmartContainer;
//import com.smart.sso.client.filter.LoginFilter;
//import com.smart.sso.client.filter.LogoutFilter;
//import com.smart.sso.client.listener.LogoutListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class SmartSsoConfig {
//
//    @Value("${sso.server.url}")
//    private String serverUrl;
//    @Value("${sso.app.id}")
//    private String appId;
//    @Value("${sso.app.secret}")
//    private String appSecret;
//
//
//    /**
//     * App登录Filter容器（可与Web方式共存）
//     *
//     * @return
//     */
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
//}
