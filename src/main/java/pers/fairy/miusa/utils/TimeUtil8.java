package pers.fairy.miusa.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 *
 * 一个基于javaSE 8的时间转换工具
 *
 *
 * @version 1.0
 * @author DZGodly
 *
 */
public class TimeUtil8 {
    // 默认格式化工具
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * 用来返回一个格式化时间后的当前时间,格式为"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String formatCurrentTime() {
        return formatCurrentTime(DEFAULT_FORMATTER);
    }

    /**
     * 用来返回一个格式化时间后的当前时间，格式自定 <br/>
     * 时区为系统默认时区
     *
     * @param formatter
     *            指定格式
     * @return 格式化后的时间
     */
    public static String formatCurrentTime(DateTimeFormatter formatter) {
        LocalDateTime currentTime = LocalDateTime.now();
        return formatter.format(currentTime);
    }

    /**
     * 根据指定格式指定时区格式化当前时间
     *
     * @param formatter
     *            指定格式
     * @param zone
     *            指定时区
     * @return 格式化后的时间
     */
    public static String formatCurrentTimeByZone(DateTimeFormatter formatter, String zone) {
        Instant now = Instant.now();
        ZoneId zoneId;
        if (zone == null) {
            zoneId = ZoneId.systemDefault();
        } else {
            zoneId = ZoneId.of(zone);
        }
        ZonedDateTime currentTime = ZonedDateTime.ofInstant(now, zoneId);
        return formatter.format(currentTime);
    }

    /**
     * 将某时间转换为指定时区的时间
     *
     * @param instant
     *            指定时刻
     * @param zone
     *            指定时区
     * @return ZonedDateTime对象，带时区的日期
     */
    public static ZonedDateTime convertToZone(Instant instant, String zone) {
        if (zone == null) {
            ZoneId zoneId = ZoneId.systemDefault();
            return instant.atZone(zoneId);
        }
        return instant.atZone(ZoneId.of(zone));
    }

    /**
     * 格式化某一时间
     *
     * @param time
     *            待格式化的时间
     * @param pattern
     *            目标格式
     * @return String对象，格式化后的时间
     */
    public static String format(Temporal time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        if (time instanceof Instant) {
            formatter = formatter.withZone(ZoneId.systemDefault());
        }
        return formatter.format(time);
    }

    // 抑制默认构造器，确保工具类不被实例化
    private TimeUtil8() {
    };
}
