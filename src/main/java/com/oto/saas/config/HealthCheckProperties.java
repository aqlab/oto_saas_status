package com.oto.saas.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.oto.saas.config
 * @Description: 健康检查阐述
 * @author: liuxin
 * @date: 2018/1/23 下午1:49
 */
@Configuration
@ConditionalOnWebApplication
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "blmEndPoint")
public class HealthCheckProperties {
    /**
     * 健康检查的url
     */
    public String statusUrl;
    /**
     * 是否开启健康检查
     */
    public boolean isEnabled;
    /**
     * 自定义日期格式
     */
    public String timeFormat;

    /**
     * 是否为私密,私密要用密码访问
     */
    private boolean isSensitive;

    public boolean isSensitive() {
        return isSensitive;
    }

    public void setSensitive(boolean sensitive) {
        isSensitive = sensitive;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getStatusUrl() {
        return statusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.statusUrl = statusUrl;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
