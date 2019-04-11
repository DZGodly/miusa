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

    @Transactional
    public OrderInfo miusa(GoodsVO goods, Long userId) throws DataAccessException {
        Long goodsId = goods.getId();
        boolean success = goodsService.reduceStock(goodsId);
        if (success) {
            return miusaOrderService.createMiusaOrder(goods, userId);
        } else {
            redisService.setMiusaOver(goodsId);
            return null;
        }
    }

    public String createMiusaPath(Long goodsId) {
        GoodsVO goods;
        if ((goods = miusaGoodsMapper.selectMiusaGoodsByGoodsId(goodsId)) == null)
            throw new GlobalException("秒杀失败，不存在该商品！");
        long current = System.currentTimeMillis();
        long start = goods.getStartDate().getTime();
        if (current < start)
            throw new GlobalException("秒杀未开始！");
        String path = UUIDUtil.getRandomPath();
        redisService.setMiusaPath(path, goodsId);
        return path;
    }

    public List<GoodsVO> listMiusaGoods() {
        return miusaGoodsMapper.selectMiusaGoods();
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

    public boolean checkPath(String path, Long goodsId) {
        String goodsIdStr = redisService.getMiusaGoodsId(path);
        return goodsId.equals(Long.valueOf(goodsIdStr));
    }

    public boolean checkEnd(GoodsVO goods, long miusaTime) {
        return goods.getEndDate().getTime() < miusaTime;
    }

    public GoodsVO getGoodsVOById(Long goodsId) {
        return miusaGoodsMapper.selectMiusaGoodsByGoodsId(goodsId);
    }
}
