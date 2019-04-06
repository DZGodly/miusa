package pers.fairy.miusa.utils;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 14:43
 */
public class ValidationUtil {

    public static boolean isMobile(String str) {
        String regex = "^((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(166)|(17[0135678])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (str == null || str.length() != 11) {
            return false;
        }
        return str.matches(regex);
    }

    private ValidationUtil() {
    }
}
