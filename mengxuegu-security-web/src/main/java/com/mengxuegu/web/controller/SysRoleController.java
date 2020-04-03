package com.mengxuegu.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/role")
@Controller
public class SysRoleController {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String HTML_PREFIX = "system/role/";

    @Autowired
    SysRoleService sysRoleService;


    /**
     * 前往用户列表页面
     */
    @GetMapping(value = {"/", ""})
    public String role() {
        return HTML_PREFIX + "role-list";
    }


    /**
     * 角色列表数据
     */
    @PostAuthorize("hasAuthority('sys:role:list')")
    @PostMapping("/page")
    @ResponseBody
    public MengxueguResult page(Page<SysRole> page, SysRole role) {

        return MengxueguResult.ok(sysRoleService.selectPage(page, role));

    }


    /**
     * 跳转新增 或 修改页面
     */
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:edit')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) {
        // 查询角色信息与绑定的权限资源
        SysRole role = sysRoleService.findById(id);
        model.addAttribute("role", role);
        return HTML_PREFIX + "role-form";
    }

    /**
     * 提交新增（POST）或更新（PUT）数据
     *
     * @param sysRole 用户角色资源
     */
    @PreAuthorize("hasAnyAuthority('sys:role:add','sys:role:edit')")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "")
    public String saveOrUpdate(SysRole sysRole) {
        sysRoleService.saveOrUpdate(sysRole);
        return "redirect:/role";
    }

    /**
     *   * 删除角色
     *   * @param id 角色id
     *   
     */
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable("id") Long id) {
        sysRoleService.deleteById(id);
        return MengxueguResult.ok();
    }


}
