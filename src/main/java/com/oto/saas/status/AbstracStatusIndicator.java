package com.oto.saas.status;

/**
 * @Package: com.oto.saas.status
 * @Description: 抽象模板
 * @author: liuxin
 * @date: 2018/1/24 上午10:51
 */
public abstract class AbstracStatusIndicator implements IStatus {
    @Override
    public final boolean status() {
        return checkout();
    }

    protected abstract boolean checkout();

}
