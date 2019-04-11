package pers.fairy.miusa.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.common.HostHolder;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/9 16:02
 */
@Service
public class MsgSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMiusaMessage(MiusaMessage miusaMessage) {
        String message = JSON.toJSONString(miusaMessage);
        amqpTemplate.convertAndSend(MQConfiguration.MIUSA_QUEUE, message);
    }
}
