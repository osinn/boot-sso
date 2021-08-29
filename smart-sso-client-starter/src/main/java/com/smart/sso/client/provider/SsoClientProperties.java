package com.smart.sso.client.provider;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author wency
 */
@Data
@ConfigurationProperties(prefix = SsoClientProperties.PREFIX)
public class SsoClientProperties {

    public static final String PREFIX = "smart.sso";

    /**
     * 单点登录服务端地址
     */
    private String serverUrl;

    /**
     * 应用的唯一标识
     */
    private String appId;

    /**
     * 私匙
     */
    private String appSecret;

    /**
     * 单点登录超时时间，默认2小时（仅需要在服务端配置，单位秒）
     */
    private int timeout = 7200;

    /**
     * FilterRegistrationBean过滤器参数
     */
    private final SsoFilter filter = new SsoFilter();

    /**
     * 单点登录Filter容器
     */
    @Data
    public static class SsoFilter {

        /**
         * 是否创建单点登录Filter容器(可自行创建过滤器指定拦截路径)
         */
        private boolean createFilter = true;

        private int order = 1;

        private String name = "smartContainer";

        private Set<String> addUrlPatterns = new LinkedHashSet();

        /**
         * 忽略拦截URL
         */
        private Set<String> excludeUrls = new LinkedHashSet<>();
    }
}
