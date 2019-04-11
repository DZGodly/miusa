package pers.fairy.miusa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.OrderStatus;
import pers.fairy.miusa.entity.OrderInfo;
import pers.fairy.miusa.mapper.MiusaOrderMapper;
import pers.fairy.miusa.mapper.OrderInfoMapper;
import pers.fairy.miusa.vo.GoodsVO;
import pers.fairy.miusa.vo.OrderDetailVO;

import java.util.Date;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/7 15:55
 */
@Service
public class OrderInfoService {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    public OrderDetailVO getOrderDetail(Long orderId) {
        return orderInfoMapper.selectOrderDetailByOrderId(orderId);
    }

    public OrderInfo createOrder(GoodsVO goods, Long userId) {
        OrderInfo info = new OrderInfo();
        info.setCreateDate(new Date());
        info.setGoodsCount(1);
        info.setGoodsId(goods.getId());
        info.setGoodsName(goods.getGoodsName());
        info.setGoodsPrice(goods.getGoodsPrice());
        info.setOrderChannel(1);
        info.setUserId(userId);
        info.setStatus(OrderStatus.UNPAID.ordinal());
        orderInfoMapper.insertSelective(info);
        return info;
    }
}
