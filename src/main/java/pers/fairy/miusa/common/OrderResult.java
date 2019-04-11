package pers.fairy.miusa.common;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/9 19:04
 */
public enum OrderResult {
    FAILED(-1), WAITING(0);

    private long value;

    public long getValue() {
        return value;
    }

    OrderResult(long value) {
        this.value = value;
    }
}
