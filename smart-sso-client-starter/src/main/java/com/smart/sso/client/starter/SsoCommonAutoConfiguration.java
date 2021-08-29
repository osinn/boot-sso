package com.smart.sso.client.starter;

import com.smart.sso.client.local.SmartContainer;
import com.smart.sso.client.local.constant.SsoConstant;
import com.smart.sso.client.local.filter.LoginFilter;
import com.smart.sso.client.local.filter.LogoutFilter;
import com.smart.sso.client.provider.SsoClientProperties;
import com.smart.sso.client.local.session.AccessTokenManager;
import com.smart.sso.client.local.session.SessionManager;
import com.smart.sso.client.local.session.TicketGrantingTicketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Set;


/**
 * @author wency
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SsoClientProperties.class)
public class SsoCommonAutoConfiguration {

    private final Environment environment;

    private final SsoClientProperties properties;

    public SsoCommonAutoConfiguration(SsoClientProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
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
    @ConditionalOnProperty(name = SsoClientProperties.PREFIX + ".filter.create-filter", havingValue = "true", matchIfMissing = true)
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

        if(addUrlPatterns.isEmpty()) {
            String contextPath = environment.getProperty("server.servlet.context-path");
            if (StringUtils.isEmpty(contextPath) || (contextPath.length() == 1 && contextPath.indexOf("/") == 0)) {
                contextPath = "/*";
            }
            log.info("server.servlet.context-path：{}", contextPath);
            addUrlPatterns.add(contextPath);
        }
        String[] arr = addUrlPatterns.toArray(new String[addUrlPatterns.size()]);
        registration.addUrlPatterns(arr);
        registration.setOrder(filter.getOrder());
        registration.setName(filter.getName());
        return registration;
    }
}
