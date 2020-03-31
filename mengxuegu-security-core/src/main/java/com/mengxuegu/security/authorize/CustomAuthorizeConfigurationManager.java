package com.mengxuegu.security.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 加载所有的授权配置
 * 类，也就是AuthorizeConfigurerProvider接口所有子类实例
 */
@Component
public class CustomAuthorizeConfigurationManager implements AuthorizeConfigurerManager {

    @Autowired
    List<AuthorizeConfigurerProvider> authorizeConfigurerProviders;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        for (AuthorizeConfigurerProvider provider : authorizeConfigurerProviders) {
            provider.configure(config);
        }
        // 除了 AuthorizeConfigurerProvider 实现类中配置的,其他请求都需要身份认证
        config.anyRequest().authenticated();


    }
}
