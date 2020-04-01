package com.mengxuegu.security;

import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("customUserDetailsService")
//public class CustomUserDetailsService implements UserDetailsService {
public class CustomUserDetailsService extends AbstractUserDetailsService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired //不可删除 只能注册一个
    PasswordEncoder passwordEncoder;

    @Autowired
    SysUserService sysUserService;

//    @Autowired
//    SysPermissionService sysPermissionService;


    @Override
    public SysUser findSysUser(String username) {
        logger.info("请求认证的用户名1：" + username);
        return sysUserService.findByUsername(username);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        logger.info("请求认证的用户名1：" + username);
//        SysUser sysUser = sysUserService.findByUsername(username);
//        // 1. 根据请求用户名向数据库中查询用户信息
//        if (sysUser == null) {
//            throw new UsernameNotFoundException("用户名或密码错误");
//        }
//        // 如果有此用户信息, 假设数据库查询到的用户密码是 1234
////        String password = passwordEncoder.encode("1234");
//
//
//        // 2.查询用户拥有权限
//        // 3.封装用户信息: username用户名,password数据库中的密码,authorities资源权限标识符
//        // SpringSecurity 底层会校验是否身份合法。
////        return new User(username, password,
////                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
////        return new User(username, password,
////                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
//        // th:if="${#authorization.expression('hasAuthority(''sys:user:add'')')}" 没权限不显示视图 authorization.expression
//        String authorityString = "sys:user,sys:role,sys:user:add,sys:role:add";
////        String authorityString = "sys:user,sys:role";
////        return new User(username, password,
////                AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString));
//
//        // 2. 查询该用户有哪一些权限
//        List<SysPermission> sysPermissions =
//                sysPermissionService.findByUserId(sysUser.getId());
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
//    }
}
