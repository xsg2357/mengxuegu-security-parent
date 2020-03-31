package com.mengxuegu.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 *  授权配置管理者接口:
 *  加载 AuthorizeConfigurerManager 所有实现类到容器中
 */
public interface AuthorizeConfigurerManager {

    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                           config);

}
