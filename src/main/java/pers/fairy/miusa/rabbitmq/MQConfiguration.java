package pers.fairy.miusa.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/9 16:04
 */
@Configuration
public class MQConfiguration {

    public final static String MIUSA_QUEUE = "miusa.queue";

    @Bean
    public Queue miusaQueue() {
        return new Queue(MIUSA_QUEUE, true);
    }
}
