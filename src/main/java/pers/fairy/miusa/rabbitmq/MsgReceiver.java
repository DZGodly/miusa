package pers.fairy.miusa.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.exception.GlobalException;
import pers.fairy.miusa.service.MiusaGoodsService;
import pers.fairy.miusa.vo.GoodsVO;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/9 16:03
 */
@Slf4j
@Service
public class MsgReceiver {

    @Autowired
    MiusaGoodsService miusaGoodsService;

    private static AtomicInteger failedTask = new AtomicInteger();

    @RabbitListener(queues = MQConfiguration.MIUSA_QUEUE)
    public void receiveMiusaMessage(String message) {
        MiusaMessage miusaMessage = JSON.parseObject(message, MiusaMessage.class);
        Long userId = miusaMessage.getUserId();
        Long goodsId = miusaMessage.getGoodsId();
        GoodsVO goods = miusaGoodsService.getGoodsVOById(goodsId);
        // 检查是否处于秒杀时间
        long miusaTime = miusaMessage.getMiusaTime();
        boolean onTime = miusaGoodsService.checkTime(goods, miusaTime);
        if (onTime) { // 如果处于秒杀时间则进行秒杀
            try {
                miusaGoodsService.miusa(goods, userId);
            } catch (Exception e) {
                log.info("failedTask - " + failedTask.incrementAndGet());
            }
        }
    }
}
