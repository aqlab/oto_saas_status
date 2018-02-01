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

    public void setNextCheckChain(AbstractCheckChain endExtCheckChain) {

        setEndCheckChain(this.nextCheckChain, endExtCheckChain, false);
    }

    public boolean setEndCheckChain(AbstractCheckChain nextCheckChain, AbstractCheckChain endExtCheckChain, boolean flag) {
        boolean Outflag = false;
        if (nextCheckChain == null) {
            Outflag = true;
        }
        if (nextCheckChain != null) {
            Outflag = setEndCheckChain(nextCheckChain.nextCheckChain, endExtCheckChain, Outflag);
            if (Outflag) {
                nextCheckChain.nextCheckChain = endExtCheckChain;
                Outflag = false;
            }
        }
        return Outflag;
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

    protected Object getBean(String beanName) {
        return webApplicationContext.getBean(beanName);
    }
}