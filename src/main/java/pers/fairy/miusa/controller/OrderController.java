package pers.fairy.miusa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.entity.OrderInfo;
import pers.fairy.miusa.service.OrderInfoService;
import pers.fairy.miusa.vo.OrderDetailVO;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/7 18:29
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 获取订单信息
     * @param orderId 订单编号
     * @return 订单信息
     */
    @GetMapping("/detail")
    @ResponseBody
    public Result getOrderInfo(@RequestParam Long orderId) {
        OrderDetailVO orderInfo = orderInfoService.getOrderDetail(orderId);
        return Result.SUCCESS(orderInfo);
    }
}
