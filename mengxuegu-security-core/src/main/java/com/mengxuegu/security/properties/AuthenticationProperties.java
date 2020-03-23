package com.mengxuegu.security.properties;

import lombok.Data;

/**
 * 安全认证相关配置
 */
@Data
public class AuthenticationProperties {

    // application.yml 没配置取默认值
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "/login/form";
    private String usernameParameter = "name";
    private String passwordParameter = "pwd";
    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};
    private String imageCodeUrl = "/code/image";// 获取图形验证码 url
    private String mobileCodeUrl=  "/code/mobile"; // 发送手机验证码 url
    private String mobilePage= "/mobile/page"; // 前往手机登录页面地址
    private Integer tokenValiditySeconds = 604800; // 记住我有效时长，单位秒， 注意不要用乘法*，会被认为字符串

    /**
     * 登录成功后响应 JSON , 还是重定向
     * 如果application.yml 中没有配置，则取此初始值 REDIRECT
     */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;

//    public LoginResponseType getLoginType() {
//        return loginType;
//    }
//
//    public void setLoginType(LoginResponseType loginType) {
//        this.loginType = loginType;
//    }
//
//
//    public String getLoginPage() {
//        return loginPage;
//    }
//
//    public void setLoginPage(String loginPage) {
//        this.loginPage = loginPage;
//    }
//
//    public String getLoginProcessingUrl() {
//        return loginProcessingUrl;
//    }
//
//    public void setLoginProcessingUrl(String loginProcessingUrl) {
//        this.loginProcessingUrl = loginProcessingUrl;
//    }
//
//    public String getUsernameParameter() {
//        return usernameParameter;
//    }
//
//    public void setUsernameParameter(String usernameParameter) {
//        this.usernameParameter = usernameParameter;
//    }
//
//    public String getPasswordParameter() {
//        return passwordParameter;
//    }
//
//    public void setPasswordParameter(String passwordParameter) {
//        this.passwordParameter = passwordParameter;
//    }
//
//    public String[] getStaticPaths() {
//        return staticPaths;
//    }
//
//    public void setStaticPaths(String[] staticPaths) {
//        this.staticPaths = staticPaths;
//    }

}
