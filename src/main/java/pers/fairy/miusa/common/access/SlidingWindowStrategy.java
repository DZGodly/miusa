package pers.fairy.miusa.common.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.utils.RedisKeyUtil;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/5/25 20:19
 */
@Component
public class SlidingWindowStrategy implements AccessLimitStrategy {
    @Autowired
    RedisAdapter redisAdapter;

    @Override
    public boolean limitAccess(Long userId, String url, int timeLimit, int maxCount) {
        String key = RedisKeyUtil.accessKey(userId, url);
        long curTime = System.currentTimeMillis();
        Pipeline pipeline = redisAdapter.piplined();
        pipeline.multi();
        pipeline.zadd(key, curTime, "" + curTime); // 访问次数 + 1
        pipeline.zremrangeByScore(key, 0, curTime - timeLimit * 1000); // 移动窗口
        Response<Long> curCount = pipeline.zcard(key);
        pipeline.expire(key, timeLimit + 1);
        pipeline.exec();
        pipeline.close();
        return curCount.get() >= maxCount; // 获取当前访问次数
    }
}
