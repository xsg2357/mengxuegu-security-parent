package com.mengxuegu.security.config;

import com.mengxuegu.security.authentication.mobile.SmsCodeSender;
import com.mengxuegu.security.authentication.mobile.SmsSend;
import com.mengxuegu.security.authentication.session.CustomInvalidSessionStrategy;
import com.mengxuegu.security.authentication.session.CustomSessionInformationExpiredStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 为容器中添加Bean实例
 */
@Configuration
public class SeurityConfigBean {

    /**
     * @ConditionalOnMissingBean(SmsSend.class) 默认采用SmsCodeSender实例 ，
     * 但如果容器中有其他 SmsSend 类型的实例，则当前实例失效
     * * 默认情况下，采用的是SmsCodeSender实例 ，
     * *   * 但是如果容器当中有其他的SmsSend类型的实例，
     * *   * 那当前的这个SmsCodeSender就失效 了
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }

    @Autowired
    private SessionRegistry sessionRegistry;

    /**
     * 失效后session处理
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {

        return new CustomInvalidSessionStrategy(sessionRegistry);
    }

    /**
     * 最多用户同时登陆session处理
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {

        return new CustomSessionInformationExpiredStrategy();
    }

}
