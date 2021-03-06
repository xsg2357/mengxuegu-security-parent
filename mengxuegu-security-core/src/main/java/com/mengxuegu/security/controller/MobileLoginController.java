package com.mengxuegu.security.controller;

import com.mengxuegu.base.result.MengxueguResult;
import com.mengxuegu.security.authentication.mobile.SmsSend;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 手机登录控制层
 */
import javax.servlet.http.HttpServletRequest;

@Controller
public class MobileLoginController {

    public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";

    /**
     * 前往登录页
     */
    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile"; // templates/login-mobile.html
    }
    
    @Autowired
    SmsSend smsSend;

    /**
     * 发送手机验证码
     */
    @ResponseBody //响应json字符串
    @RequestMapping("/code/mobile")
    public MengxueguResult smsCode(HttpServletRequest request) {
        // 1. 生成一个手机验证码
        String code = RandomStringUtils.randomNumeric(4);
        // 2. 将手机验证码保存到session中
        request.getSession().setAttribute(SESSION_KEY, code);
        // 3. 发送验证码到用户手机上
        String mobile = request.getParameter("mobile");
        smsSend.sendSms(mobile, code);
        return MengxueguResult.ok();
    }

}
