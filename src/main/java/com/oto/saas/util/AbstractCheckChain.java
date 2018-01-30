package com.oto.saas.util;


import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @Package: com.oto.saas.util
 * @Description: 检查链路
 * @author: liuxin
 * @date: 2018/1/30 下午2:35
 */
public abstract class AbstractCheckChain {
    protected AbstractCheckChain nextCheckChain;
    protected static ApplicationContext webApplicationContext;

    protected static final String SUCCESS = "online";
    protected static final String FAIL = "fail";

    public AbstractCheckChain() {
    }

    public AbstractCheckChain getNextCheckChain() {
        return nextCheckChain;
    }

    public void setNextCheckChain(AbstractCheckChain nextCheckChain) {
        this.nextCheckChain = nextCheckChain;
    }

    protected AbstractCheckChain(AbstractCheckChain nextCheckChain) {
        if (webApplicationContext == null) webApplicationContext = SaasGateWayTools.getWebApplicationContext();
        this.nextCheckChain = nextCheckChain;
    }

    public void checkChain(Map<String, Object> appStatus) {
        Check(appStatus);
        if (nextCheckChain != null) {
            nextCheckChain.checkChain(appStatus);
        }
    }

    public abstract void Check(Map<String, Object> appStatus);

    protected static String formatStatus(boolean flag) {
        if (flag) {
            return SUCCESS;
        }
        return FAIL;
    }

    protected boolean containsBean(String beanName) {
       return webApplicationContext.containsBean(beanName);
    }

    protected Object getBean(String beanName){
        return webApplicationContext.getBean(beanName);
    }
}