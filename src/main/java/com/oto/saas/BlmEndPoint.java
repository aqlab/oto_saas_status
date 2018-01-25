package com.oto.saas;

import com.oto.saas.config.HealthCheckProperties;
import com.oto.saas.util.SaasGateWayTools;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.util.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Package: com.oto.saas
 * @Description: 自定义监控端口
 * @author: liuxin
 * @date: 2018/1/23 下午1:46
 */
public class BlmEndPoint extends AbstractEndpoint implements Endpoint {

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
     *
     */
    private boolean isSensitive;


    public BlmEndPoint(HealthCheckProperties healthCheckProperties) {
        super(healthCheckProperties.getStatusUrl(), healthCheckProperties.isSensitive());
        this.statusUrl = healthCheckProperties.getStatusUrl();
        this.isEnabled = healthCheckProperties.isEnabled();
        this.isSensitive = healthCheckProperties.isSensitive();
    }

    /**
     * 对外暴露的url
     *
     * @return
     */
    @Override
    public String getId() {
        return this.statusUrl;
    }

    /**
     * 是否开启
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * 是否是敏感的
     * 如果是敏感的要用密码访问
     *
     * @return
     */
    @Override
    public boolean isSensitive() {
        return this.isSensitive;
    }

    @Override
    public Object invoke() {
        return SaasGateWayTools.status();
    }


}
