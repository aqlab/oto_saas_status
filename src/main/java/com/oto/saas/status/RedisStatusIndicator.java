package com.oto.saas.status;

import redis.clients.jedis.JedisPool;

/**
 * @Package: com.oto.saas.status
 * @Description:
 * @author: liuxin
 * @date: 2018/1/24 上午10:55
 */
public class RedisStatusIndicator extends AbstracStatusIndicator {
    private JedisPool jedisPool;

    public RedisStatusIndicator(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    protected boolean checkout() {
        boolean flag = false;
        try {
            flag = jedisPool.getResource().isConnected();
        } catch (Exception e) {
            System.err.print("redis 连接失败: "+e.getMessage());
            return false;
        }
        return flag;
    }
}
