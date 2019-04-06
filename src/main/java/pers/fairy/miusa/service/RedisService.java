package pers.fairy.miusa.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.fairy.miusa.common.RedisAdapter;
import pers.fairy.miusa.entity.User;
import pers.fairy.miusa.utils.RedisKeyUtil;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 16:10
 */
@Service
public class RedisService {
    @Autowired
    RedisAdapter redisAdapter;

    public String setToken(User user, String token) {
        String key = RedisKeyUtil.tokenKey(token);
        return redisAdapter.set(key, JSON.toJSONString(user));
    }

    public String getHtml(String pageName) {
        return redisAdapter.get(pageName);
    }

    public String setHtml(String pageName, String page, int seconds) {
        return redisAdapter.setex(pageName, page, seconds);
    }
}
