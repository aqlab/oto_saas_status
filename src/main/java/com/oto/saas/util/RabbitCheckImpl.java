package com.oto.saas.util;

import com.oto.saas.status.RabbitStatusIndicator;

import java.util.Map;

/**
 * @Package: com.oto.saas.util
 * @Description: ${todo}
 * @author: liuxin
 * @date: 2018/1/30 下午2:40
 */
public class RabbitCheckImpl extends AbstractCheckChain {

    public RabbitCheckImpl() {
    }

    public RabbitCheckImpl(AbstractCheckChain nextCheckChain) {
        super(nextCheckChain);
    }

    @Override
    public void Check(Map<String, Object> appStatus) {
        if (!containsBean("blm_rabbitStatusIndicator")) {
            appStatus.put("rabbit", "-");
            return;
        }
        RabbitStatusIndicator rabbitStatusIndicator = (RabbitStatusIndicator) getBean("blm_rabbitStatusIndicator");
        appStatus.put("rabbit", formatStatus(rabbitStatusIndicator.status()));
    }
}
