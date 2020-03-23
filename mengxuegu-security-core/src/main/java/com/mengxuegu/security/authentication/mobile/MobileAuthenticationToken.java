package com.mengxuegu.security.authentication.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 手机验证码登录产生token 参照UsernamePasswordAuthenticationToken实现
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;//认证前是手机号 认证后是用户信息
//    private Object credentials;

    /**
     *  开始认证时,创建一个MobileAuthenticationToken实例 接收的是手机号码, 并且 标识未认证
     * @param principal 手机号
     */
    public MobileAuthenticationToken(Object principal) {
        super((Collection) null);
        this.principal = principal;
//        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    /**
     * 当认证通过后,会重新创建一个新的MobileAuthenticationToken,来标识它已经认证通过
     * @param principal 用户信息
     * @param authorities 权限资源
     */
    public MobileAuthenticationToken(Object principal,  Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
//        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    /**
     * 在父类中是一个抽象方法,所以要实现, 但是它是密码,而当前不需要,则直接返回null
     */
    public Object getCredentials() {
        return null;
    }


    /**
     * 手机号码
     */
    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
//        this.credentials = null;
    }
}
