package pers.fairy.miusa.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/4 19:40
 */
public class MD5Util {

    public static String encrypt(String msg){
        return DigestUtils.md5Hex(msg);
    }

    private MD5Util(){}
}
