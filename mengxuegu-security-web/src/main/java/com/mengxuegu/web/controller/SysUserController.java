package com.mengxuegu.web.controller;

import com.mengxuegu.base.result.MengxueguResult;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/user")
@Controller
public class SysUserController {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String HTML_PREFIX = "system/user/";

    /**
     * 前往用户列表页面
     */
    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping(value = {"/", ""})
    public String user() {
        return HTML_PREFIX + "user-list";
    }


    /**
     * 跳转到新增或者修改页面
     */
    // 有'sys:user:add' 或 'sys:user:edit'权限的可以访问
    @PreAuthorize("hasAnyAuthority('sys:user:add','sys:user:edit')")
    @GetMapping(value = {"/form"})
    public String form() {
        return HTML_PREFIX + "user-form";
    }

    /**
     * 删除用户
     */
    // 返回值的code等于200，则调用成功，不然抛出403不允许访问
    @PostAuthorize("returnObject.code == 200")
    @RequestMapping("/{id}")
    @ResponseBody
    public MengxueguResult deleteById(@PathVariable Long id) {
        if (id < 0) {
            return MengxueguResult.build(500, "参数不能小于0");
        }
        return MengxueguResult.ok();
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

}
