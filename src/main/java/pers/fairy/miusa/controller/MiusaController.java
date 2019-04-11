package pers.fairy.miusa.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.OrderResult;
import pers.fairy.miusa.common.Result;
import pers.fairy.miusa.common.access.AccessLimit;
import pers.fairy.miusa.entity.MiusaOrder;
import pers.fairy.miusa.entity.OrderInfo;
import pers.fairy.miusa.exception.EmptyGoodsListException;
import pers.fairy.miusa.exception.GlobalException;
import pers.fairy.miusa.rabbitmq.MiusaMessage;
import pers.fairy.miusa.rabbitmq.MsgSender;
import pers.fairy.miusa.service.GoodsService;
import pers.fairy.miusa.service.MiusaGoodsService;
import pers.fairy.miusa.service.MiusaOrderService;
import pers.fairy.miusa.service.RedisService;
import pers.fairy.miusa.utils.UUIDUtil;
import pers.fairy.miusa.vo.GoodsVO;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/7 14:18
 */
@Controller
@RequestMapping("/miusa")
public class MiusaController implements InitializingBean {

    @Autowired
    private MiusaGoodsService miusaGoodsService;
    @Autowired
    private MiusaOrderService miusaOrderService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private MsgSender msgSender;

    // 内存标记商品是否售罄
    private CopyOnWriteArraySet<Long> goodsNotInStock = new CopyOnWriteArraySet<>();

    // 初始化，将商品库存加载入内存
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsList = miusaGoodsService.listMiusaGoods();
        if (goodsList == null)
            throw new EmptyGoodsListException("无任何商品可载入");
        for (GoodsVO goods : goodsList) {
            Long goodsId = goods.getId();
            Integer stockCount = goods.getStockCount();
            redisService.preloadStock(goodsId, stockCount);
        }
    }

    @GetMapping("/path")
    @AccessLimit(seconds = 60, maxCount = 5)
    @ResponseBody
    public Result getMiusaPath(@RequestParam Long goodsId) {
        String path = miusaGoodsService.createMiusaPath(goodsId);
        return Result.SUCCESS(path);
    }

    @PostMapping("/{path}/do_miusa")//
    @ResponseBody
    public Result doMiusa(@RequestParam Long goodsId,
                          @PathVariable String path) {
        boolean isValidPath = miusaGoodsService.checkPath(path, goodsId);
        if (!isValidPath) {
            return Result.ERROR("非法路径");
        }
        // 从内存中查看商品是否售罄
        if (goodsNotInStock.contains(goodsId)) {
            return Result.GOOD_ERROR("手慢一步！商品已售罄");
        }
        // 访问 redis，预减库存
        Long stock = redisService.preReduceStock(goodsId);
        if (stock < 0) {
            goodsNotInStock.add(goodsId); // 在内存中标记售罄的商品，避免下次请求访问 redis 产生的开销。
            return Result.GOOD_ERROR("手慢一步！商品已售罄");
        }
        // 判断是否已经秒杀
        MiusaOrder miusaOrder = miusaOrderService.getMiusaOrderByGoodsId(goodsId);
        if (miusaOrder != null) {
            return Result.ORDER_ERROR("您已下单，请前往查看");
        }
        // 将订单放到消息队列异步处理，然后返回排队等候的状态
        Long userId = hostHolder.getUser().getId();
        long current = System.currentTimeMillis();
        MiusaMessage message = new MiusaMessage();
        message.setGoodsId(goodsId);
        message.setUserId(userId);
        message.setMiusaTime(current);
        msgSender.sendMiusaMessage(message);
        return Result.SUCCESS(OrderResult.WAITING.getValue());
    }

    @GetMapping("/result")
    @ResponseBody
    public Result getMiusaResult(@RequestParam long goodsId) {
        long result = miusaOrderService.getMiusaResult(goodsId);
        return Result.SUCCESS(result);
    }

}
