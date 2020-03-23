package com.mengxuegu.security.authentication.mobile;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import com.mengxuegu.security.authentication.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

/**
 * 将定义的手机短信认证相关的组件组合起来，一起添加到容器中
 */
@Component
public class MobileAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain
        , HttpSecurity> {

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    UserDetailsService mobileUserDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //        super.configure(http);
        // 创建校验手机号过滤器实例
        MobileAuthenticationFilter mobileAuthenticationFilter
                = new MobileAuthenticationFilter();
        // 接收 AuthenticationManager 认证管理器
        mobileAuthenticationFilter.setAuthenticationManager(
                http.getSharedObject(AuthenticationManager.class));
        //记住我实现功能
        mobileAuthenticationFilter.setRememberMeServices(http.getSharedObject(RememberMeServices.class));
        // session 会话管理功能 解决同一用户的手机重复登录问题
        // 正常应该是同一个用户，系统中只能用用户名或手机号登录一次。
        mobileAuthenticationFilter.setSessionAuthenticationStrategy(
                http.getSharedObject(SessionAuthenticationStrategy.class));
        // 采用哪个成功、失败处理器
        mobileAuthenticationFilter.setAuthenticationSuccessHandler(
                customAuthenticationSuccessHandler);

        mobileAuthenticationFilter.setAuthenticationFailureHandler(
                customAuthenticationFailureHandler);
        // 为 Provider 指定明确 的mobileUserDetailsService 来查询用户信息
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(mobileUserDetailsService);
        // 将 provider 绑定到 HttpSecurity 上面，
        // 并且将 手机认证加到 用户名密码认证之后
        http.authenticationProvider(provider)
                .addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
