package com.oto.saas.status;

import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Package: com.oto.saas.status
 * @Description:
 * @author: liuxin
 * @date: 2018/1/24 上午10:55
 */
public class RabbitStatusIndicator extends AbstracStatusIndicator {
    private RabbitTemplate rabbitTemplate;

    public RabbitStatusIndicator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    protected boolean checkout() {
        ConnectionFactory connectionFactory = null;
        boolean flag = false;
        try {
            connectionFactory = rabbitTemplate.getConnectionFactory();
            Connection connection = connectionFactory.createConnection();
            flag = connection.isOpen();
            connection.close();
        } catch (Exception e) {
            System.err.print(e.getMessage());
            return false;
        }
        return flag;
    }
}
