package pers.fairy.miusa.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.OrderResult;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.entity.MiusaOrder;
import pers.fairy.miusa.entity.OrderInfo;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.mapper.MiusaOrderMapper;
import pers.fairy.miusa.utils.RedisKeyUtil;
import pers.fairy.miusa.vo.GoodsVO;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/7 16:13
 */
@Service
public class MiusaOrderService {

    @Autowired
    private MiusaOrderMapper miusaOrderMapper;
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private HostHolder hostHolder;

    @Transactional
    public OrderInfo createMiusaOrder(GoodsVO goods, Long userId) throws DataAccessException{
        Long goodsId = goods.getId();
        OrderInfo orderInfo = orderInfoService.createOrder(goods, userId);
        MiusaOrder miusaOrder = new MiusaOrder();
        miusaOrder.setGoodsId(goodsId);
        miusaOrder.setOrderId(orderInfo.getId());
        miusaOrder.setUserId(userId);
        miusaOrderMapper.insertSelective(miusaOrder);

        redisService.setMiusaOrder(goodsId, miusaOrder);
        return orderInfo;
    }

    public long getMiusaResult(Long goodsId) {
        MiusaOrder miusaOrder = redisService.getMiusaOrder(goodsId);
        long result;
        if (miusaOrder != null) { // 秒杀成功返回订单 ID
            result = miusaOrder.getOrderId();
        } else {
            if (redisService.isMiusaOver(goodsId))
                result = OrderResult.FAILED.getValue();
            else
                result = OrderResult.WAITING.getValue();
        }
        return result;
    }

    public MiusaOrder getMiusaOrderByGoodsId(Long goodsId) {
        return redisService.getMiusaOrder(goodsId);
    }
}
