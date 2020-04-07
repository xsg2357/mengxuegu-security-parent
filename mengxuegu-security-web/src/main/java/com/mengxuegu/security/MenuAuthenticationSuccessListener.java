package com.mengxuegu.security;

import com.mengxuegu.security.authentication.AuthenticationSuccessListener;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证成功后触发此类
 * 1.认证成功后触发此类CustomAuthenticationSuccessHandler
 * 2.定义一个监听器AuthenticationSuccessListener及其监听器方法successListener
 * 3.创建一个MenuAuthenticationSuccessListener继承AuthenticationSuccessListener来获取用户权限
 * 4.在 com.mengxuegu.security.AbstractUserDetailsService#findSysPermission 查询用户信息时已经将用户
 * 权限信息存放到了 com.mengxuegu.web.entities.SysUser#permissions 中，最终认证通过后会将 SysUser
 * 对象放到Authentication的 principal 属性中 。
 * 5. 在 MenuAuthenticationSuccessListener  的实现方法中从Authentication 中获取到 principal ，它其实就是
 * sysUser 对象 ,意味着就可以获取 permissions权限集合，来进行处理渲染左侧菜单。
 */
@Component
public class MenuAuthenticationSuccessListener implements AuthenticationSuccessListener {


    /**
     * 方便成功后，查询用户所拥有的权限
     */
    @Override
    public void successListener(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof SysUser) {
            SysUser sysUser = (SysUser) principal;
            // 将sysUser.permissions的权限，重组下父子关系放到子菜单集合中
            loadMenuTree(sysUser);
            // 重组到 sysUser 后，通过 authentication.principal.permissions就可以进行获取到
        }
    }

    /**
     * 重组下父子关系放到子菜单集合中
     *
     * @param sysUser sysUser
     */
    private void loadMenuTree(SysUser sysUser) {
        // 获取所有的权限资源
        List<SysPermission> permissions = sysUser.getPermissions();
        // 无权限
        if (permissions.isEmpty()) {
            return;
        }
        //获取所有菜单(没有按钮)
        List<SysPermission> menuList = new ArrayList<>();
        for (SysPermission p : permissions) {
            if (p.getType().equals(1)) {
                // 类型为1表示菜单
                menuList.add(p);
            }
        }
        // 收集每个菜单的子菜单传入到对应的 children 集合中
        buildChildren(menuList);
        // 返回值: 只要获取所有根菜单(一级菜单) 即可, 因为根菜单中已经在上面收集了子菜单
        List<SysPermission> result = new ArrayList<>();
        for (SysPermission menu : menuList) {
            // 判断是否为根菜单
            if (menu.getParentId().equals(0L)) {
                // 父id为0表示根菜单
                result.add(menu);
            }
        }
        // 重新设置到 permissions 集合中
        sysUser.setPermissions(result);
    }

    /**
     * 获取子菜单
     *
     * @param menuList menuList
     */
    private void buildChildren(List<SysPermission> menuList) {
        for (SysPermission menu : menuList) {
            // 存入每个菜单对应的子菜单
            List<SysPermission> childMenu = new ArrayList<>();
            //样式 active
            List<String> childUrl = new ArrayList<>();
            // 获取每个菜单(menuList) 下的子菜单（menu）
            for (SysPermission p : menuList) {
                if (menu.getId().equals(p.getParentId())) {
                    childMenu.add(p);
                    childUrl.add(p.getUrl());
                }
            }
            // 设置子菜单
            menu.setChildren(childMenu);
            menu.setChildrenUrl(childUrl);
        }
    }
}
