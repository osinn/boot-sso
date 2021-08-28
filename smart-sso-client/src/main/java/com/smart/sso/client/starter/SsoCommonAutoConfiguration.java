package com.smart.sso.client.starter;

import com.smart.sso.client.local.SmartContainer;
import com.smart.sso.client.local.filter.LoginFilter;
import com.smart.sso.client.local.filter.LogoutFilter;
import com.smart.sso.client.local.provider.SsoClientProperties;
import com.smart.sso.client.local.session.AccessTokenManager;
import com.smart.sso.client.local.session.SessionManager;
import com.smart.sso.client.local.session.TicketGrantingTicketManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


/**
 * @author wency
 */
@Configuration
@EnableConfigurationProperties(SsoClientProperties.class)
public class SsoCommonAutoConfiguration {


    private final SsoClientProperties properties;

    public SsoCommonAutoConfiguration(SsoClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SessionManager sessionManager(AccessTokenManager accessTokenManager, TicketGrantingTicketManager ticketGrantingTicketManager) {
        return new SessionManager(accessTokenManager, ticketGrantingTicketManager);
    }

    /**
     * 单点登录Filter容器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<SmartContainer> smartContainer() {

        SmartContainer smartContainer = new SmartContainer();
        smartContainer.setServerUrl(properties.getServerUrl());
        smartContainer.setAppId(properties.getAppId());
        smartContainer.setAppSecret(properties.getAppSecret());
        SsoClientProperties.SsoFilter filter = properties.getFilter();
        Set<String> excludeUrls = filter.getExcludeUrls();

        String join = String.join(",", excludeUrls);
//        join += "/login,/logout,/oauth2/*,/custom/*,/assets/*";
        // 忽略拦截URL,多个逗号分隔
        smartContainer.setExcludeUrls(join);

        smartContainer.setFilters(new LogoutFilter(), new LoginFilter());

        FilterRegistrationBean<SmartContainer> registration = new FilterRegistrationBean<>();
        registration.setFilter(smartContainer);

        Set<String> addUrlPatterns = filter.getAddUrlPatterns();
        addUrlPatterns.add("/*");
        String[] arr = addUrlPatterns.toArray(new String[addUrlPatterns.size()]);
        registration.addUrlPatterns(arr);
        registration.setOrder(filter.getOrder());
        registration.setName(filter.getName());
        return registration;
    }
}
