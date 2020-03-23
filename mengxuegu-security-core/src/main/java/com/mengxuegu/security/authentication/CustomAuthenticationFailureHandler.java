package com.mengxuegu.security.authentication;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.properties.LoginResponseType;
import com.mengxuegu.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(
                securityProperties.getAuthentication().getLoginType())) {

            // 认证失败状态码 401
            MengxueguResult result = MengxueguResult.build(
                    HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        } else {
            // 重写向回认证页面，注意加上 ?error 1.0
//            super.setDefaultFailureUrl(
//                    securityProperties.getAuthentication().getLoginPage() + "?error");

            // 重写向回认证页面，注意加上 ?error 1.1
            //获取上一次请求路径
            String referer = request.getHeader("Referer");
            logger.info("referer:" + referer);
//            String lastUrl = StringUtils.substringBefore(referer, "?");
            // 如果需要认证，跳转 /login/page, 否则截取 1.2
            Object toAuthentication = request.getAttribute("toAuthentication");
            String lastUrl = toAuthentication != null ?
                    securityProperties.getAuthentication().getLoginPage() :
                    StringUtils.substringBefore(referer, "?");

            logger.info("上一次请求的路径 ：" + lastUrl);
            super.setDefaultFailureUrl(lastUrl + "?error");
            super.onAuthenticationFailure(request, response, exception);
        }

    }
}
