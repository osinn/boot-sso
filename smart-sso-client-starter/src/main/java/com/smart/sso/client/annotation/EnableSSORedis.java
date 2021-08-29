package com.smart.sso.client.annotation;

import com.smart.sso.client.starter.SsoCommonAutoConfiguration;
import com.smart.sso.client.starter.SsoRedisClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用服务端session redis管理方式
 *
 * @author wency_cai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SsoRedisClientAutoConfiguration.class, SsoCommonAutoConfiguration.class})
public @interface EnableSSORedis {

}
