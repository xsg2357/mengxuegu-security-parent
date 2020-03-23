package com.mengxuegu.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mengxuegu.security") //Factory method 'springSecurityFilterChain' threw exception
public class SecurityProperties {

    // 将application.yml 中的 mengxuegu.security.authentication 下面的值绑定到此对象中
    private AuthenticationProperties authentication;

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }

}
