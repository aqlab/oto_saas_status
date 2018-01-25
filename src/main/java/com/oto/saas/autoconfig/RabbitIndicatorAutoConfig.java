package com.oto.saas.autoconfig;
import com.oto.saas.status.RabbitStatusIndicator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.oto.saas.config
 * @Description: 当mongo存在于项目中才去加载配置bean
 * @author: liuxin
 * @date: 2018/1/23 下午1:53
 */
@Configuration
@ConditionalOnClass(RabbitTemplate.class)
@ConditionalOnBean(RabbitTemplate.class)
@AutoConfigureAfter(RabbitAutoConfiguration.class)
public class RabbitIndicatorAutoConfig {
    @Bean(value = "blm_rabbitStatusIndicator")
    public RabbitStatusIndicator rabbitStatusIndicator(@Autowired RabbitTemplate rabbitTemplate){
        return new RabbitStatusIndicator(rabbitTemplate);
    }
}
