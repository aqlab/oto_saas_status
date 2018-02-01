package com.oto.saas.util;

import com.oto.saas.config.BlmConfig;
import com.oto.saas.config.HealthCheckProperties;
import com.oto.saas.status.MongoStatusIndicator;
import com.oto.saas.status.RabbitStatusIndicator;
import com.oto.saas.status.RedisStatusIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.jca.context.SpringContextResourceAdapter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Package: com.oto.saas.util
 * @Description:
 * @author: liuxin
 * @date: 2018/1/24 上午11:21
 */
public class SaasGateWayTools {
    private static ApplicationContext webApplicationContext;
    private static Map<String, Object> appConfig;
    private static HealthCheckProperties healthCheckProperties;
    private static AbstractCheckChain checkChain;

    public static void setWebApplicationContext(ApplicationContext applicationContext, Map<String, Object> config, HealthCheckProperties healthProperties) {
        webApplicationContext = applicationContext;
        appConfig = config;
        healthCheckProperties = healthProperties;
        initDefaultCheckChain();
    }

    //如果扩展这个检查链路? 只需要在项目启动时候,摄入指定的检查链路,即可
    private static void initDefaultCheckChain() {
        checkChain = new MongoCheckImpl(new RabbitCheckImpl(new RedisCheckImpl()));
    }

    public static void updateCheckChain(AbstractCheckChain extCheckChain) {
        checkChain = extCheckChain;
    }

    public static void addCheckChain(AbstractCheckChain nextCheckChain) {
        checkChain.setNextCheckChain(nextCheckChain);
    }

    public static Map<String, Object> status() {
        Map<String, Object> appStatus = new LinkedHashMap<>();
        appStatus.put("time", time());
        appStatus.put(BlmConfig.CUSTOMER_APP_NAME, appConfig.getOrDefault(BlmConfig.CUSTOMER_APP_NAME, "系统未定义"));
        appStatus.put("host", getLoopHost());
        appStatus.put("port", appConfig.get(BlmConfig.CUSTOMER_APP_PORT));
        appStatus.put("active", appConfig.get(BlmConfig.CUSTOMER_APP_ACTIVE));
        checkChain.checkChain(appStatus);
        return appStatus;
    }


    private static String getLoopHost() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = (InetAddress) addresses.nextElement();
                if (ip != null
                        && ip instanceof Inet4Address
                        && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                        && ip.getHostAddress().indexOf(":") == -1) {
                    return ip.getHostAddress();
                }
            }
        }
        return "127.0.0.1";
    }


    private static String time() {
        String timeFormat = healthCheckProperties.getTimeFormat();
        if (StringUtils.isEmpty(timeFormat)) {
            timeFormat = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
        return LocalDateTime.now().format(formatter).toString();
    }

    public static ApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public static void setWebApplicationContext(ApplicationContext webApplicationContext) {
        SaasGateWayTools.webApplicationContext = webApplicationContext;
    }
}
