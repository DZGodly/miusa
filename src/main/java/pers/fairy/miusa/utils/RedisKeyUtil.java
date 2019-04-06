package pers.fairy.miusa.utils;


/**
 * @author ：DZGodly
 * @date ：Created in 2019/3/19 13:15
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";

    public static String pvKey(int entityType, int entityId) {
        return "PAGE:" + entityType + SPLIT + entityId;
    }

    public static String tokenKey(String token) {
        return "TOKEN:" + token;
    }

    private RedisKeyUtil() {
    }
}
