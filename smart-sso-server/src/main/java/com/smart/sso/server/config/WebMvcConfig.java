package com.smart.sso.server.config;

import java.util.ArrayList;
import java.util.List;

import com.smart.sso.client.local.SmartContainer;
import com.smart.sso.client.local.filter.LoginFilter;
import com.smart.sso.client.local.filter.LogoutFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Value("${smart.sso.server-url}")
    private String serverUrl;
    @Value("${smart.sso.app-id}")
    private String appId;
    @Value("${smart.sso.app-secret}")
    private String appSecret;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.TEXT_HTML);
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(converter);
    }

    /**
     * 单点登录Filter容器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<SmartContainer> smartContainer() {
        SmartContainer smartContainer = new SmartContainer();
        smartContainer.setServerUrl(serverUrl);
        smartContainer.setAppId(appId);
        smartContainer.setAppSecret(appSecret);

        // 忽略拦截URL,多个逗号分隔
        smartContainer.setExcludeUrls("/login,/logout,/oauth2/*,/custom/*,/assets/*");

        smartContainer.setFilters(new LogoutFilter(), new LoginFilter());

        FilterRegistrationBean<SmartContainer> registration = new FilterRegistrationBean<>();
        registration.setFilter(smartContainer);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        registration.setName("smartContainer");
        return registration;
    }
}
