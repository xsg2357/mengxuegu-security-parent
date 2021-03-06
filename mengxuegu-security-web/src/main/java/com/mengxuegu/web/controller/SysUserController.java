package com.mengxuegu.web.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.web.entities.SysRole;
import com.mengxuegu.web.entities.SysUser;
import com.mengxuegu.web.service.SysRoleService;
import com.mengxuegu.web.service.SysUserService;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@Controller
public class SysUserController {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static final String HTML_PREFIX = "system/user/";

    @Autowired
    SysUserService sysUserService;


    /**
     * 前往用户列表页面
     */
    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping(value = {"/", ""})
    public String user() {
        return HTML_PREFIX + "user-list";
    }


    /**
     * 跳转到新增或者修改页面 测试时使用
     */
    // 有'sys:user:add' 或 'sys:user:edit'权限的可以访问
    @PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
    @GetMapping(value = {"/form1"})
    public String form1() {
        return HTML_PREFIX + "user-form";
    }

    /**
     * 删除用户 测试使用
     */
    // 返回值的code等于200，则调用成功，不然抛出403不允许访问
//    @PostAuthorize("returnObject.code == 200")
//    @RequestMapping("/{id1}")
//    @ResponseBody
//    public MengxueguResult deleteById1(@PathVariable Long id1) {
//        if (id1 < 0) {
//            return MengxueguResult.build(500, "参数不能小于0");
//        }
//        return MengxueguResult.ok();
//    }


    /**
     * 分页：用户列表数据
     * 方法调用完成后进行权限检查
     *
     * @param page 分页
     * @param user 搜索条件
     */
    @PostAuthorize("hasAuthority('sys:user:list')")
    @PostMapping("/page") // 不要少了 /
    @ResponseBody // 不要少了
    public MengxueguResult page(Page<SysUser> page, SysUser user) {
        System.out.println(JSON.toJSONString(sysUserService.selectPage(page, user).getRecords()));
        return MengxueguResult.ok(sysUserService.selectPage(page, user));
    }


    /**
     * 批量删除
     */
    // 测试：http://localhost/user/batch/-1,0,1,2 ，响应 {"code":200,"message":"OK","data":[1,2]}
    // filterTarget 过滤集合属性名, filterObject集合中的元素,只接收id>0的数据
    @PreFilter(filterTarget = "ids", value = "filterObject > 0")
    @RequestMapping("/batch/{ids}")
    @ResponseBody
    public MengxueguResult deleteByIds(@PathVariable List<Long> ids) {
        return MengxueguResult.ok(ids);
    }

    // filterObject 是返回值集合中每个元素，过滤掉当前登录用户数据，返回其他用户的数据
    @PostFilter("filterObject != authentication.principal.username")
    @RequestMapping("/list")
    @ResponseBody
    public List<String> page() {
        return Lists.newArrayList("meng", "test", "admin");
    }

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 跳转新增 或 修改页面
     */
    @PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
    @GetMapping(value = {"/form", "/form/{id}"})
    public String form(@PathVariable(required = false) Long id, Model model) {
        //查询所有角色
        List<SysRole> roleList = sysRoleService.list();
        model.addAttribute("roleList", roleList);
        //查询用户信息与拥有角色
        SysUser user = sysUserService.findById(id);
        model.addAttribute("user", user);
        return HTML_PREFIX + "user-form";
    }

    /**
     * 提交新增（POST）或更新（PUT）数据
     */
    @PostAuthorize("hasAuthority('sys:user:add') or hasAuthority('sys:user:edit')")
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "")
    public String saveOrUpdate(SysUser sysUser) {
        sysUserService.saveOrUpdate(sysUser);
        return "redirect:/user";
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @PostAuthorize("hasAuthority('sys:user:delete')")
    @RequestMapping("/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable Long id) {
        sysUserService.deleteById(id);
        return MengxueguResult.ok();
    }


}
