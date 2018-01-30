package com.oto.saas.util;

import com.oto.saas.status.RedisStatusIndicator;

import java.util.Map;

/**
 * @Package: com.oto.saas.util
 * @Description: ${todo}
 * @author: liuxin
 * @date: 2018/1/30 下午2:40
 */
public class RedisCheckImpl extends AbstractCheckChain {
    public RedisCheckImpl() {

    }

    public RedisCheckImpl(AbstractCheckChain nextCheckChain) {
        super(nextCheckChain);
    }

    @Override
    public void Check(Map<String, Object> appStatus) {
        if (!containsBean("blm_redisStatusIndicator")) {
            appStatus.put("redis", "-");
            return;
        }
        RedisStatusIndicator redisStatusIndicator = (RedisStatusIndicator) getBean("blm_redisStatusIndicator");
        appStatus.put("redis", formatStatus(redisStatusIndicator.status()));
    }
}
