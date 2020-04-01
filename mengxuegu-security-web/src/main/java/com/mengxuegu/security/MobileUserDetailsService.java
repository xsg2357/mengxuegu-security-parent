package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysPermissionService;
import com.mengxuegu.web.service.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 通过手机号查询用户信息
 */
@Component("mobileUserDetailsService")
//public class MobileUserDetailsService implements UserDetailsService {
public class MobileUserDetailsService extends AbstractUserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    SysUserService sysUserService;

//    @Autowired
//    SysPermissionService sysPermissionService;


    @Override
    public SysUser findSysUser(String mobile) {
        logger.info("请求的手机号是：" + mobile);
        return sysUserService.findByMobile(mobile);
    }

//    @Override
//    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
//        logger.info("请求的手机号是：" + mobile);
//        SysUser sysUser = sysUserService.findByMobile(mobile);
//        // 1. 根据请求用户名向数据库中查询用户信息
//        if (sysUser == null) {
//            throw new UsernameNotFoundException("该手机号未注册");
//        }
//
//        // 1. 通过手机号查询用户信息 根据手机号去获取用户名
//        // 2. 如果有此用户，则查询用户权限
//        // 3. 封装用户信息
////        return new User(mobile, "", //并不是保存手机号 而是保存用户名
////                true, true, true, true,
////                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
//
//        // 2. 查询该用户有哪一些权限
//        List<SysPermission> sysPermissions = sysPermissionService.findByUserId(sysUser.getId());
//        // 无权限
//        if (CollectionUtils.isEmpty(sysPermissions)) {
//            return sysUser;
//        }
//        // 3. 封装用户信息和权限信息
//        List<GrantedAuthority> authorities = Lists.newArrayList();
//        for (SysPermission sp : sysPermissions) {
//            //权限标识
//            authorities.add(new SimpleGrantedAuthority(sp.getCode()));
//        }
//        sysUser.setAuthorities(authorities);
//        //4. 交给 Spring Security 框架完成身份认证操作
//        return sysUser;
//
//
//    }
}
