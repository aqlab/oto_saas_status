package com.oto.saas.autoconfig;

import com.oto.saas.BlmEndPoint;
import com.oto.saas.config.BlmConfig;
import com.oto.saas.config.HealthCheckProperties;
import com.oto.saas.util.SaasGateWayTools;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Package: com.oto.saas.config
 * @Description:
 * @author: liuxin
 * @date: 2018/1/23 下午1:53
 */
@Configuration
@EnableConfigurationProperties({HealthCheckProperties.class})
public class BlmEndPointAutoConfig {

    @Bean
    public BlmEndPoint blmEndPoint(HealthCheckProperties healthCheckProperties, Environment environment, ApplicationContext applicationContext) {
        this.initGateWayConfig(environment,healthCheckProperties,applicationContext);
        this.checkDefaultHealthProperties(healthCheckProperties);
        return new BlmEndPoint(healthCheckProperties);
    }

    public void initGateWayConfig(Environment environment,HealthCheckProperties healthCheckProperties,ApplicationContext applicationContext){
        SaasGateWayTools.setWebApplicationContext(applicationContext,getCurrentWebBaseConfig(environment),healthCheckProperties);
    }


    private Map<String, Object> getCurrentWebBaseConfig(Environment environment) {
        Map<String, Object> appConfig = new LinkedHashMap<>();
        String appName = environment.getProperty(BlmConfig.APPNAME);
        String port = environment.getProperty(BlmConfig.PORT);
        String active = environment.getProperty(BlmConfig.ACTIVE);
        appConfig.put(BlmConfig.CUSTOMER_APP_NAME, appName);
        appConfig.put(BlmConfig.CUSTOMER_APP_PORT, port);
        appConfig.put(BlmConfig.CUSTOMER_APP_ACTIVE, active);
        return appConfig;
    }

    private void checkDefaultHealthProperties(HealthCheckProperties healthCheckProperties) {
        if (StringUtils.isEmpty(healthCheckProperties.getStatusUrl())) {
            healthCheckProperties.setStatusUrl("status");
        }
        if (StringUtils.isEmpty(healthCheckProperties.getTimeFormat())) {
            healthCheckProperties.setTimeFormat("yyyy-MM-dd HH:mm:ss");
        }
    }
}
