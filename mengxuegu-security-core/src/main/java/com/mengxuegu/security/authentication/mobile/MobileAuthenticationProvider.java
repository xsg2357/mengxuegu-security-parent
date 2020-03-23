package com.mengxuegu.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机认证处理提供者
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    /**
     * * 处理认证:
     * 通过这个方法 来选择对应的provider
     * * 1. 通过 手机号 去数据库查询用户信息(UserDeatilsService)
     * * 2. 再重新构建认证信息
     * * @param authentication
     * * @return
     * * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken mobileAuthenticationToken
                = (MobileAuthenticationToken) authentication;
        // 获取用户输入的手机号
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        // 查询数据库
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if (userDetails == null) {
            throw new AuthenticationServiceException("该手机未注册");
        }
        // 查询到了用户信息, 则认证通过,就重新构建 MobileAuthenticationToken 实例
        MobileAuthenticationToken authenticationToken =
                new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationToken;
    }

    /**
     * * 通过此方法,来判断 采用哪一个 AuthenticationProvider
     * * @param authentication
     * * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }


    /**
     * 注入 MobileUserDetailsService
     *
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
