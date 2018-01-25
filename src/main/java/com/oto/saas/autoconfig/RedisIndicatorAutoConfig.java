package com.oto.saas.autoconfig;
import com.oto.saas.status.RedisStatusIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import redis.clients.jedis.JedisPool;


/**
 * @Package: com.oto.saas.config
 * @Description: 当mongo存在于项目中才去加载配置bean
 * @author: liuxin
 * @date: 2018/1/23 下午1:53
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
@ConditionalOnBean(RedisConnectionFactory.class)
@AutoConfigureAfter(RedisRepositoriesAutoConfiguration.class)
public class RedisIndicatorAutoConfig {
    @Bean(value = "blm_redisStatusIndicator")
    public RedisStatusIndicator redisStatusIndicator(@Autowired JedisPool jedisPool){
        return new RedisStatusIndicator(jedisPool);
    }
}
