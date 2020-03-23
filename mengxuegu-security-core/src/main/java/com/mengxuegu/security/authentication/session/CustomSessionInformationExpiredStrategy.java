package com.mengxuegu.security.authentication.session;

import com.mengxuegu.security.authentication.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Autowired

    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        // 退出的用户
        UserDetails userDetails =
                (UserDetails) event.getSessionInformation().getPrincipal();
        AuthenticationException exception =
                new AuthenticationServiceException(String.format("[%s]用户在另外一台电脑登录，您已被下线",
                        userDetails.getUsername()));
        try {
            event.getRequest().setAttribute("toAuthentication", true);
            customAuthenticationFailureHandler.onAuthenticationFailure(
                    event.getRequest(), event.getResponse(), exception);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
