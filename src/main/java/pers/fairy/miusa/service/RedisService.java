package pers.fairy.miusa.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.common.HostHolder;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.entity.MiusaOrder;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.utils.RedisKeyUtil;

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

    public Long accessCount(Long userId, String url, int seconds) {
        String key = RedisKeyUtil.accessKey(userId, url);
        Long count = redisAdapter.incr(key);
        redisAdapter.expire(key, seconds);
        return count;
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

    public void setMiusaOver(Long goodsId) {
        String key = RedisKeyUtil.miusaOverKey();
        redisAdapter.sadd(key, goodsId + "");
    }

    public boolean isMiusaOver(Long goodsId) {
        String key = RedisKeyUtil.miusaOverKey();
        return redisAdapter.sismember(key, goodsId + "");
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
