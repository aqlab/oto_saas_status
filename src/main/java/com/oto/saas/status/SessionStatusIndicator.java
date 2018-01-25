package com.oto.saas.status;

/**
 * @Package: com.oto.saas.status
 * @Description: session缓存的检查
 * @author: liuxin
 * @date: 2018/1/24 上午10:55
 */
public class SessionStatusIndicator extends AbstracStatusIndicator {
    @Override
    protected boolean checkout() {
        return false;
    }
}
