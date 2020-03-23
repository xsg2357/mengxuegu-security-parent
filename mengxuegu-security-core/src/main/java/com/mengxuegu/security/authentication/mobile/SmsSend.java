package com.mengxuegu.security.authentication.mobile;

/**
 * 短信发送统一接口
 */
public interface SmsSend {

    /**
     *
     * @param mobile 手机号
     * @param content 短信内容
     * @return true 成功
     */
    boolean sendSms(String mobile,String content);
}
