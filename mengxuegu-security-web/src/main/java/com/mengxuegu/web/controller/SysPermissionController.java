package com.mengxuegu.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/permission")
@Controller
public class SysPermissionController {

    Logger logger = LoggerFactory.getLogger(getClass());

    public static final String HTML_PREFIX = "system/permission/";

    /**
     * 前往用户列表页面
     */
    @GetMapping(value = {"/", ""})
    public String permission() {
        return HTML_PREFIX + "permission-list";
    }


}
