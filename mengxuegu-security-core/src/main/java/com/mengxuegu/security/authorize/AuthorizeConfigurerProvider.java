package com.mengxuegu.security.authorize;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置统一接口, 所有模块授权配置类都实现此接口
 */
public interface AuthorizeConfigurerProvider {


//                .authorizeRequests() // 认证请求
//                .antMatchers(sProperties.getAuthentication().getLoginPage(),
////                        "/code/image","/mobile/page","/code/mobile"
//                        sProperties.getAuthentication().getImageCodeUrl(),
//                        sProperties.getAuthentication().getMobileCodeUrl(),
//                        sProperties.getAuthentication().getMobilePage()
//
//                ).permitAll() //localhost 将您重定向的次数过多。对所有人放行
////                .antMatchers("/user").hasRole("ADMIN")
////                  hasAnyRole设置角色会加上前缀 "ROLE_" 作为前缀
////                  hasAnyAuthority设置角色不会拼接前缀 "ROLE_" 使用其中任何一个
////                .antMatchers("/user").hasAnyRole("ADMIN","ROOT")//设置角色会加上前缀 "ROLE_"
//                .antMatchers("/user").hasAuthority("sys:user")//设置角色会不加上前缀 "ROLE_"
//                .antMatchers(HttpMethod.GET, "/role").hasAuthority("sys:role")
//                .antMatchers(HttpMethod.GET, "/permission")//角色会加上前缀 ROLE_，即真实是 ROLE_ADMIN
//                .access("hasAuthority('sys:permission') or hasAnyRole('ADMIN')")
////                .antMatchers("/user").hasAnyAuthority("ADMIN","ROOT")//设置角色会加上前缀 "ROLE_"
//                .anyRequest().authenticated() // 所有进入应用的HTTP请求都要进行认证

    /**
     * 参数authorizeRequests 的返回值CustomAuthorizeConfigurationProvider
     */
    void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
