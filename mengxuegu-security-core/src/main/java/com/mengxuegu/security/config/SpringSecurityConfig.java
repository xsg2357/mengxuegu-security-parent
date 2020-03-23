package com.mengxuegu.security.config;

import com.mengxuegu.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity //启动 SpringSecurity 过滤器链功能
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        // 用户信息存储在内存中 1.0
//        auth.inMemoryAuthentication().withUser("mengxuegu")
//                .password("1234").authorities("ADMIN");


        // 1.1
        String password = passwordEncoder().encode("1234");
        logger.info("加密之后存储的密码：" + password);
        auth.inMemoryAuthentication().withUser("mengxuegu")
                .password(password).authorities("ADMIN");

    }

    @Autowired
    private SecurityProperties sProperties;


    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic() //浏览器表现出弹窗

        http.formLogin() //浏览器表现出登录网页 表单登录
//                .loginPage("/login/page")  // ==>CustomLoginController ->toLogin
                .loginPage(sProperties.getAuthentication().getLoginPage())  // ==>CustomLoginController ->toLogin
//                .loginProcessingUrl("/login/form") // 登录表单提交处理Url, 默认是 /login
                .loginProcessingUrl(sProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理Url, 默认是 /login
//                .usernameParameter("name") // 默认用户名的属性名是 username
                .usernameParameter(sProperties.getAuthentication().getUsernameParameter()) // 默认用户名的属性名是 username
//                .passwordParameter("pwd") // 默认密码的属性名是 password
                .passwordParameter(sProperties.getAuthentication().getPasswordParameter()) // 默认密码的属性名是 password
                .and()

                .authorizeRequests() // 认证请求
                .antMatchers(sProperties.getAuthentication().getLoginPage()).permitAll() //localhost 将您重定向的次数过多。对所有人放行
                .anyRequest().authenticated() // 所有进入应用的HTTP请求都要进行认证
//                .and().csrf().disable() // 输入有效用户信息，还是回到登录页，则要禁用 CSRF 攻击。
                // CSRF（Cross-site request forgery） 跨站请求伪造 关闭 CSRF 攻击
        ; // 分号`;`不要少了
    }

    /**
     * 对静态资源放行
     */
    @Override
    public void configure(WebSecurity web) {
//        super.configure(web);
//        web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
        web.ignoring().antMatchers(sProperties.getAuthentication().getStaticPaths());
    }
}
