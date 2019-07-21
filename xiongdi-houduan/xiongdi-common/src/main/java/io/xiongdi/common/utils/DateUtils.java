package io.xiongdi.common.utils;


import io.xiongdi.common.exception.XDException;
import io.xiongdi.common.validator.Assert;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;

/**
 * @author wujiaxing
 * @LocalDateTime 2019-07-17
 */
public class DateUtils {

    /**
     * 获取格式化器
     * @param pattern 需要转换的格式
     * @return 格式化器
     */
    public static DateTimeFormatter formatter(String pattern) {
        Assert.isNull(pattern, "需要转换的格式为空");
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * 将字符串转换成 LocalDate 对象
     * @param convertTarger 需要转换的字符串
     * @param pattern
     * @return
     */
    public static LocalDate stringToDate(String convertTarger, FormatPattern pattern) {
        return stringToDate(convertTarger, pattern.pattern());
    }

    /**
     * 将字符串转换成 LocalDate 对象
     * @param convertTarger 需要转换的字符串
     * @param pattern
     * @return
     */
    public static LocalDate stringToDate(String convertTarger, String pattern) {
        Assert.isNull(convertTarger, "需要转换的日期字符串为空");
        if (pattern == null) {
            return LocalDate.parse(convertTarger);
        }
        return LocalDate.parse(pattern);
    }
    /**
     * 将字符串转换成 LocalDate 对象
     * @param convertTarger 需要转换的字符串
     * @return
     */
    public static LocalDate stringToDate(String convertTarger) {
        return stringToDate(convertTarger, FormatPattern.TIME_PATTERN.pattern());
    }

    /**
     * 将字符串转换成 LocalDateTime 对象
     * @param convertTarger 需要转换的字符串
     * @param pattern
     * @return
     */
    public static LocalDateTime stringToDateTime(String convertTarger, FormatPattern pattern) {
        return stringToDateTime(convertTarger, pattern.pattern());
    }

    /**
     * 将字符串转换成 LocalDateTime 对象
     * @param convertTarger 需要转换的字符串
     * @param pattern
     * @return
     */
    public static LocalDateTime stringToDateTime(String convertTarger, String pattern) {
        Assert.isNull(convertTarger, "需要转换的日期字符串为空");
        if (pattern == null) {
            return LocalDateTime.parse(convertTarger);
        }
        return LocalDateTime.parse(pattern);
    }
    /**
     * 将字符串转换成 LocalDateTime 对象
     * @param convertTarger 需要转换的字符串
     * @return
     */
    public static LocalDateTime stringToDateTime(String convertTarger) {
        return stringToDateTime(convertTarger, FormatPattern.TIME_PATTERN.pattern());
    }

    /**
     * 将字符串转换成 LocalTime 对象
     * @param convertTarger 需要转换的字符串
     * @param pattern
     * @return
     */
    public static LocalTime stringToTime(String convertTarger, FormatPattern pattern) {
        return stringToTime(convertTarger, pattern.pattern());
    }

    /**
     * 将字符串转换成 LocalTime 对象
     * @param convertTarger 需要转换的字符串
     * @param pattern
     * @return
     */
    public static LocalTime stringToTime(String convertTarger, String pattern) {
        Assert.isNull(convertTarger, "需要转换的日期字符串为空");
        if (pattern == null) {
            return LocalTime.parse(convertTarger);
        }
        return LocalTime.parse(pattern);
    }
    /**
     * 将字符串转换成 LocalTime 对象
     * @param convertTarger 需要转换的字符串
     * @return
     */
    public static LocalTime stringToTime(String convertTarger) {
        return stringToTime(convertTarger, FormatPattern.TIME_PATTERN.pattern());
    }

    /**
     * 将日期格式化成字符串
     * @param t 需要格式化的日期对象，是 TemporalAccessor 的实现类
     * @param pattern 格式风格
     * @param <T>
     * @return 格式化后的字符串
     * @see TemporalAccessor
     */
    public static <T extends TemporalAccessor>String dateToString(T t, String pattern) {
        if(t == null) {
            throw new XDException("转换的日期为空");
        }
        return formatter(pattern).format(t);
    }
    /**
     * 将日期格式化成字符串
     * @param t 需要格式化的日期对象，是 TemporalAccessor 的实现类
     * @param <T>
     * @return 格式化后的字符串
     * @see TemporalAccessor
     */
    public static <T extends TemporalAccessor>String dateToString(T t) {
        return dateToString(t, FormatPattern.TIME_PATTERN.pattern());
    }

    /**
     * 根据周数，获取日期的开始时间和结束时间
     * @param week week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static LocalDate[] getWeekStartAndEnd(int week) {
        LocalDateTime currentDay = LocalDateTime.now().plusWeeks(week);
        LocalDateTime startDay = currentDay.plusDays(-(currentDay.getDayOfWeek().getValue() - 1) );
        LocalDateTime endDay = currentDay.plusDays(7 - currentDay.getDayOfWeek().getValue());
        return new LocalDate[] {startDay.toLocalDate(), endDay.toLocalDate()};
    }
    /**
     * 对日期的【毫秒秒】进行加/减
     * @param dateTime 目标日期
     * @param MilliSeconds 需要加减的秒数
     * @return 处理过后的日期
     */
    public static LocalDateTime addDateMilliSecond(LocalDateTime dateTime, int MilliSeconds) {
        Assert.isNull(dateTime, "目标日期错误");
        return dateTime.plus(MilliSeconds, ChronoUnit.MILLIS);
    }


    /**
     * 对日期的【秒】进行加/减
     * @param dateTime 目标日期
     * @param seconds 需要加减的秒数
     * @return 处理过后的日期
     */
    public static LocalDateTime addDateSeconds(LocalDateTime dateTime, int seconds) {
        Assert.isNull(dateTime, "目标日期错误");
        return dateTime.plusSeconds(seconds);
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param dateTime 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static LocalDateTime addDateMinutes(LocalDateTime dateTime, int minutes) {
        return dateTime.plusMinutes(minutes);
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param dateTime 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static LocalDateTime addDateHours(LocalDateTime dateTime, int hours) {
        return dateTime.plusHours(hours);
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param dateTime 日期
     * @param days 天数，负dateTime减
     * @return 加/减几天后的日期
     */
    public static LocalDateTime addDateDays(LocalDateTime dateTime, int days) {
        return dateTime.plusDays(days);
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param dateTime 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static LocalDateTime addDateWeeks(LocalDateTime dateTime, int weeks) {
        return dateTime.plusWeeks(weeks);
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param dateTime 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static LocalDateTime addDateMonths(LocalDateTime dateTime, int months) {
        return dateTime.plusMonths(months);
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param dateTime 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static LocalDateTime addDateYears(LocalDateTime dateTime, int years) {
        return dateTime.plusYears(years);
    }

    /**
     *  将日期转成毫秒数
     * @param dateTime
     * @return
     */
    public static long toMilliSeconds(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获取当前时间的毫秒数
     * @return
     */
    public static long currentMillSeconds(){
        return Instant.now().toEpochMilli();
    }

}
