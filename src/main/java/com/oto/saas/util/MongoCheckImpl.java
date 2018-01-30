package com.oto.saas.util;

import com.oto.saas.status.MongoStatusIndicator;

import java.util.Map;

/**
 * @Package: com.oto.saas.util
 * @Description:
 * @author: liuxin
 * @date: 2018/1/30 下午2:40
 */
public class MongoCheckImpl extends AbstractCheckChain {
    public MongoCheckImpl() {
    }
    public MongoCheckImpl(AbstractCheckChain nextCheckChain) {
        super(nextCheckChain);
    }

    @Override
    public void Check(Map<String, Object> appStatus) {
        if (!containsBean("blm_mongoStatusIndicator")) {
            appStatus.put("mongo", "-");
            return;
        }
        MongoStatusIndicator mongoStatusIndicator = (MongoStatusIndicator)getBean("blm_mongoStatusIndicator");
        appStatus.put("mongo", formatStatus(mongoStatusIndicator.status()));
    }
}
