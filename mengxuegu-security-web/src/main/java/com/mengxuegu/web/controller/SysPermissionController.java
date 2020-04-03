package com.mengxuegu.web.controller;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.web.entities.SysPermission;
import com.mengxuegu.web.service.SysPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/permission")
@Controller
public class SysPermissionController {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String HTML_PREFIX = "system/permission/";

    /**
     * 前往用户列表页面
     */
    @PreAuthorize("hasAuthority('sys:permission')")
    @GetMapping(value = {"/", ""})
    public String permission() {
        return HTML_PREFIX + "permission-list";
    }


    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 权限资源列表
     */
    @PreAuthorize("hasAuthority('sys:permission:list')")
    @PostMapping("list")
    @ResponseBody
    public MengxueguResult list() {
        return MengxueguResult.ok(sysPermissionService.list());
    }

    /**
     * 跳转新增或者修改页面
     *
     * @param id    权限Id
     * @param model 响应的数据模型
     * @PathVariable(required = false) 表示某个字段非必须
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:add','sys:permission:edit')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) {
        // 通过权限id查询权限资源
        SysPermission permission = sysPermissionService.getById(id);
        model.addAttribute("permission", permission == null ? new SysPermission() : permission);
        return HTML_PREFIX + "permission-form";
    }

    /**
     * 提交或者新增
     *
     * @param sysPermission 用户要更新或者提交的权限资源
     */
    @PreAuthorize("hasAnyAuthority('sys:permission:add','sys:permission:edit')")
    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.PUT})
    public String saveOrUpdate(SysPermission sysPermission) {
        // saveOrUpdate 是 MyBatis-Plus中的IService内置的
        sysPermissionService.saveOrUpdate(sysPermission);
        return "redirect:/permission";
    }


    /**
     * 删除权限资源
     *
     * @param id 权限资源 id
     */
    @PreAuthorize("hasAuthority('sys:permission:delete')")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable("id") Long id) {
        sysPermissionService.deleteById(id);
        return MengxueguResult.ok();
    }


}
