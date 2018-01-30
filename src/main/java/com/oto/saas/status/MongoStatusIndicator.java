package com.oto.saas.status;

import com.mongodb.CommandResult;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @Package: com.oto.saas.status
 * @Description:
 * @author: liuxin
 * @date: 2018/1/24 上午10:54
 */
public class MongoStatusIndicator extends AbstracStatusIndicator {

    private MongoTemplate mongoTemplate;

    public MongoStatusIndicator(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    protected boolean checkout() {
        boolean flag = false;
        CommandResult result = null;
        try {
            result = mongoTemplate.executeCommand("{ buildInfo: 1 }");
            flag = result.ok();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
        return flag;
    }
}
