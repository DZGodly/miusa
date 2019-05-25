package pers.fairy.miusa.common.access;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/5/25 20:12
 */
public interface AccessLimitStrategy {
    boolean limitAccess(Long userId, String url, int timeLimit, int maxCount);
}
