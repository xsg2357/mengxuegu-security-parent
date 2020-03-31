package com.mengxuegu.security.authorize;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 关于系统管理模块相关的授权配置提供者
 */
@Component
public class SystemAuthorizeConfigurerProvider implements AuthorizeConfigurerProvider {


    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/user").hasAuthority("sys:user")
                .antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
                .antMatchers(HttpMethod.GET, "/permission")
                //角色会加上前缀 ROLE_，即真实是 ROLE_ADMIN
                .access("hasAuthority('sys:permission') or hasAnyRole('ADMIN')");

    }
}
