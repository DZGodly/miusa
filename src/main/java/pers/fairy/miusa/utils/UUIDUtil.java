package pers.fairy.miusa.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 16:05
 */
public class UUIDUtil {

    public static String salt() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    public static String token() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getRandomPath() {
        Random random = new Random();
        String rdmPath = UUID.randomUUID().toString().replace("-", "");
        return MD5Util.encrypt(rdmPath + random.nextInt(10_000));
    }

    private UUIDUtil() {
    }
}
