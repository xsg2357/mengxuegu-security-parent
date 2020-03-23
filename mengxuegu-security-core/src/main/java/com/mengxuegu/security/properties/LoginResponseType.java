package com.mengxuegu.security.properties;

import com.alibaba.fastjson.JSON;

/**
 * 认证响应类型
 */
public enum LoginResponseType {

    /**
     * 响应 JSON 字符串
     */
    JSON,
    /**
     * 重定向地址
     */
    REDIRECT

}
