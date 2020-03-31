package com.mengxuegu.security.authorize;

import com.mengxuegu.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 身份认证的相关配置
 */
@Component
@Order(Integer.MAX_VALUE) //值越小越优先
public class CustomAuthorizeConfigurationProvider implements AuthorizeConfigurerProvider {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

        // 放行/login/page不需要认证可访问
        config.antMatchers(securityProperties.getAuthentication().getLoginPage(),
                securityProperties.getAuthentication().getImageCodeUrl(),
                securityProperties.getAuthentication().getMobilePage(),
                securityProperties.getAuthentication().getMobileCodeUrl()
        ).permitAll();

        //所有其他请求都要通过身份认证
//        config.anyRequest().authenticated();


    }
}
