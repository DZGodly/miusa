package pers.fairy.miusa.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/3/18 19:45
 */
@Slf4j
@Component
public class RedisAdapter implements InitializingBean {

    private JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool();
    }

    public Long expire(String key, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.expire(key, seconds);
        }
    }

    public String setex(String key, String value, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.setex(key, seconds, value);
        }
    }

    public String set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        }
    }

    public List<String> lrange(String key, long start, long stop) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lrange(key, start, stop);
        }
    }

    public Set<String> zrange(String key, long start, long stop) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrange(key, start, stop);
        }
    }

    public Set<String> zrangebyscore(String key, String min, String max) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrangeByScore(key, min, max);
        }
    }

    public Set<String> zrevrangebyscore(String key, String max, String min) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrangeByScore(key, max, min);
        }
    }

    public Double zscore(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscore(key, member);
        }
    }

    public Long zcard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcard(key);
        }
    }

    public List<Object> multiZAdd(List<ZSetModel> zSetModels) {
        try (Jedis jedis = jedisPool.getResource()) {
            try (Transaction tx = jedis.multi()) {
                for (ZSetModel zSetModel : zSetModels) {
                    tx.zadd(zSetModel.getKey(), zSetModel.getScore(), zSetModel.getMember());
                }
                return tx.exec();
            }
        } catch (Exception e) {
            log.error("multiZAdd 失败！\n" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Object> multiZRem(List<ZSetModel> zSetModels) {
        try (Jedis jedis = jedisPool.getResource()) {
            try (Transaction tx = jedis.multi()) {
                for (ZSetModel zSetModel : zSetModels) {
                    tx.zrem(zSetModel.getKey(), zSetModel.getMember());
                }
                return tx.exec();
            }
        } catch (Exception e) {
            log.error("multiZRem 失败！\n" + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<String> blpop(String... key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.blpop(0, key);
        }
    }

    public Long lpush(String key, String... s) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.lpush(key, s);
        }
    }

    public Long sadd(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sadd(key, members);
        }
    }

    public Long srem(String key, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.srem(key, members);
        }
    }

    public Long scard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.scard(key);
        }
    }

    public Boolean sismember(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(key, member);
        }
    }

    public Long incr(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incr(key);
        }
    }

    public Long decr(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decr(key);
        }
    }

    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }
}
