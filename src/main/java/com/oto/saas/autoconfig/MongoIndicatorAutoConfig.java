package com.oto.saas.autoconfig;
import com.oto.saas.status.MongoStatusIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @Package: com.oto.saas.config
 * @Description: 当mongo存在于项目中才去加载配置bean
 * @author: liuxin
 * @date: 2018/1/23 下午1:53
 */
@Configuration
@ConditionalOnClass(MongoTemplate.class)
@ConditionalOnBean(MongoTemplate.class)
@AutoConfigureAfter(MongoRepositoriesAutoConfiguration.class)
public class MongoIndicatorAutoConfig {
    @Bean(value = "blm_mongoStatusIndicator")
    public MongoStatusIndicator mongoStatusIndicator(@Autowired MongoTemplate mongoTemplate){
        return new MongoStatusIndicator(mongoTemplate);
    }
}
