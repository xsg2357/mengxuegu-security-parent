package com.mengxuegu;

import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysPermissionService;
import com.mengxuegu.web.service.SysRoleService;
import com.mengxuegu.web.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestWebApplication {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;


    @Autowired
    SysPermissionService sysPermissionService;


    @Test
    public void testSysUser() {
        SysUser sysUser = sysUserService.findByUsername("admin");
        System.out.println("sysUser:"+sysUser);
        // // 这是 MyBatis-Plus 内置的方法
        List<SysUser> list = sysUserService.list();
        System.out.println("list:" + list.toString());
    }


    @Test
    public void testSysRole() {
        SysRole sysRole = sysRoleService.getById(9);
        System.out.println("sysRole:"+sysRole);
    }


    @Test
    public void testSysPermission() {
        List<SysPermission> sysPermissions = sysPermissionService.findByUserId(9L);
        System.out.println("sysPermission:"+sysPermissions);
    }
}
