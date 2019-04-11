package pers.fairy.miusa.utils;


/**
 * @author ：DZGodly
 * @date ：Created in 2019/3/19 13:15
 */
public class RedisKeyUtil {

    private static final String SPLIT = ":";

    public static String accessKey(Long userId, String url) {
        return "ACCESS:" + url + SPLIT + userId;
    }

    public static String stockKey(Long goodsId) {
        return "STOCK:" + goodsId;
    }

    public static String tokenKey(String token) {
        return "TOKEN:" + token;
    }

    public static String MiusaPathKey(String path, Long id) {
        return "MIUSA_PATH:" + id + SPLIT + path;
    }

    public static String miusaOrderKey(Long userId, Long goodsId) {
        return "MIUSA_ORDER:" + goodsId + SPLIT + userId;
    }

    public static String miusaOverKey() {
        return "MIUSA_OVER:";
    }

    private RedisKeyUtil() {
    }
}
