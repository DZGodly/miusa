package pers.fairy.miusa.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.common.access.AccessLimitStrategy;
import pers.fairy.miusa.entity.MiusaOrder;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.utils.RedisKeyUtil;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 16:10
 */
@Service
public class RedisService {
    @Autowired
    private RedisAdapter redisAdapter;
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private AccessLimitStrategy accessLimitStrategy;

    /**
     * 查询访问频率
     *
     * @param userId    访问者id
     * @param url       请求路径
     * @param timeLimit 时间限制
     * @param maxCount  最大访问次数
     * @return 是否允许访问
     */
    public boolean isAccessAllowed(Long userId, String url, int timeLimit, int maxCount) {
        return !accessLimitStrategy.limitAccess(userId, url, timeLimit, maxCount);
    }

    /**
     * 检查秒杀商品是否存在。
     *
     * @param goodsId 商品 id
     * @return 存在则返回true，否则返回 false。
     */
    public Boolean checkMiusaGoods(Long goodsId) {
        String key = RedisKeyUtil.stockKey(goodsId);
        return redisAdapter.exists(key);
    }

    public String preloadStock(Long goodsId, Integer stockCount) {
        String key = RedisKeyUtil.stockKey(goodsId);
        return redisAdapter.set(key, stockCount + "");
    }

    public Long preReduceStock(Long goodsId) {
        String key = RedisKeyUtil.stockKey(goodsId);
        return redisAdapter.decr(key);
    }

    public String setToken(User user, String token) {
        String key = RedisKeyUtil.tokenKey(token);
        return redisAdapter.set(key, JSON.toJSONString(user));
    }

    public String setMiusaPath(String path, Long goodsId) {
        User currentUser = hostHolder.getUser();
        String key = RedisKeyUtil.MiusaPathKey(path, currentUser.getId());
        return redisAdapter.setex(key, goodsId + "", 600);
    }

    public void setMiusaFailed(Long goodsId, Long userId) {
        String key = RedisKeyUtil.miusaFailedKey(goodsId);
        redisAdapter.sadd(key, userId + "");
    }

    public boolean isMiusaFailed(Long goodsId, Long userId) {
        String key = RedisKeyUtil.miusaFailedKey(goodsId);
        return redisAdapter.sismember(key, userId + "");
    }

    public void setMiusaOrder(Long goodsId, MiusaOrder miusaOrder) {
        String key = RedisKeyUtil.miusaOrderKey(miusaOrder.getUserId(), goodsId);
        redisAdapter.set(key, JSON.toJSONString(miusaOrder));
    }

    public MiusaOrder getMiusaOrder(Long goodsId) {
        User currentUser = hostHolder.getUser();
        String key = RedisKeyUtil.miusaOrderKey(currentUser.getId(), goodsId);
        return JSON.parseObject(redisAdapter.get(key), MiusaOrder.class);
    }

    public String getMiusaGoodsId(String path) {
        User currentUser = hostHolder.getUser();
        String key = RedisKeyUtil.MiusaPathKey(path, currentUser.getId());
        return redisAdapter.get(key);
    }

    public String getHtml(String pageName) {
        return redisAdapter.get(pageName);
    }

    public String setHtml(String pageName, String page, int seconds) {
        return redisAdapter.setex(pageName, page, seconds);
    }


}
