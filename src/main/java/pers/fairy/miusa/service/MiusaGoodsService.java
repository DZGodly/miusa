package pers.fairy.miusa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.entity.MiusaGoods;
import pers.fairy.miusa.entity.OrderInfo;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.exception.GlobalException;
import pers.fairy.miusa.mapper.MiusaGoodsMapper;
import pers.fairy.miusa.utils.RedisKeyUtil;
import pers.fairy.miusa.utils.UUIDUtil;
import pers.fairy.miusa.vo.GoodsDetailVO;
import pers.fairy.miusa.vo.GoodsVO;

import java.util.List;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/6 13:22
 */
@Service
public class MiusaGoodsService {
    @Autowired
    private MiusaGoodsMapper miusaGoodsMapper;
    @Autowired
    private MiusaOrderService miusaOrderService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 秒杀商品接口，首先减少库存，成功后生成订单，失败则表示商品库存不足，设置秒杀商品结束。
     *
     * @param goods  商品
     * @param userId 用户编号
     * @return 下单成功则返回订单信息，否则返回 null
     * @throws DataAccessException 数据库异常，将重复下单产生的异常交给上层处理
     */
    @Transactional
    public OrderInfo miusa(GoodsVO goods, Long userId) throws DataAccessException {
        Long goodsId = goods.getId();
        boolean success = goodsService.reduceStock(goodsId);
        if (success) {
            return miusaOrderService.createMiusaOrder(goods, userId);
        } else {
            redisService.setMiusaFailed(goodsId,userId);
            return null;
        }
    }

    /**
     * 生成秒杀入口路径。
     * 先检查商品 id 是否存在，不存在则抛出异常；
     *
     * @param goodsId 商品 id
     * @return 秒杀入口路径
     */
    public String createMiusaPath(Long goodsId) {
        if (!redisService.checkMiusaGoods(goodsId))
            throw new GlobalException("秒杀失败，不存在该商品！");
        String path = UUIDUtil.getRandomPath();
        redisService.setMiusaPath(path, goodsId);
        return path;
    }

    public List<GoodsVO> listMiusaGoods() {
        return miusaGoodsMapper.selectMiusaGoods();
    }

    // 检查路径是否存在
    public boolean checkPath(String path, Long goodsId) {
        String goodsIdStr = redisService.getMiusaGoodsId(path);
        return goodsId.equals(Long.valueOf(goodsIdStr));
    }

    // 检查当前是否是秒杀活动时间
    public boolean checkTime(GoodsVO goods, long miusaTime) {
        long start = goods.getStartDate().getTime();
        long end = goods.getEndDate().getTime();
        return start <= miusaTime && miusaTime <= end;
    }

    public GoodsDetailVO getGoodsDetailVOByGoodsId(Long goodsId) {
        GoodsVO goods = miusaGoodsMapper.selectMiusaGoodsByGoodsId(goodsId);
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setGoods(goods);
        goodsDetailVO.setUser(hostHolder.getUser());
        long start = goods.getStartDate().getTime();
        long end = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        long remainSeconds;
        if (now >= end) {
            remainSeconds = -1;
        } else if (now <= start) {
            remainSeconds = (start - now) / 1000;
        } else {
            remainSeconds = 0;
        }
        goodsDetailVO.setRemainSeconds(remainSeconds);
        return goodsDetailVO;
    }

    public GoodsVO getGoodsVOById(Long goodsId) {
        return miusaGoodsMapper.selectMiusaGoodsByGoodsId(goodsId);
    }
}
