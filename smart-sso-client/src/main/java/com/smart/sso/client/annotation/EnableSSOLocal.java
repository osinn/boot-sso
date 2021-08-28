package com.smart.sso.client.annotation;

import com.smart.sso.client.starter.SsoClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用服务端session local管理方式
 *
 * @author wency_cai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SsoClientAutoConfiguration.class)
public @interface EnableSSOLocal {

}
