package io.xiongdi.common.utils;

/**
 * 格式化日期时间风格枚举类
 * @author wujiaxing
 * @date 2019-07-20
 */
public enum FormatPattern {
    /**
     * 一天的毫秒数
     */
    DAYMILLI(24 * 60 * 60 * 1000),
    /**
     * 一小时的毫秒数
     */
    HOURMILLI(60 * 60 * 1000),
    /**
     * 一分钟的毫秒数
     */
    MINUTEMILLI(60 * 1000),
    /**
     * 一秒钟的毫秒数
     */
    SECONDMILLI(1000),
    /**
     * 日期时间格式 dd/MMM/yyyy:HH:mm:ss +0900
     */
    TIME_PATTERN_LONG("dd/MMM/yyyy:HH:mm:ss +0900"),
    /**
     * 日期时间格式 dd/MM/yyyy:HH:mm:ss +0900
     */
    TIME_PATTERN_LOGN2("dd/MM/yyyy:HH:mm:ss +0900"),
    /**
     * 日期时间格式 YYYY-MM-DD HH24:MI:SS
     */
    DB_TIME_PATTERN("YYYY-MM-DD HH24:MI:SS"),
    /**
     * 日期时间格式 YYYYMMDDHH24MISS
     */
    DB_TIME_PATTERN_1("YYYYMMDDHH24MISS"),
    /**
     * 日期时间格式 dd/MM/yy HH:mm:ss
     */
    STRING_PATTERN_SHORT("dd/MM/yy HH:mm:ss"),
    /**
     * 日期时间格式 yyyy/MM/dd HH:mm
     */
    STRING_PATTERN_SHORT_1("yyyy/MM/dd HH:mm"),
    /**
     * 日期时间格式 yyyy年MM月dd日 HH:mm:ss
     */
    STRING_PATTERN_SHORT_2("yyyy年MM月dd日 HH:mm:ss"),
    /**
     * 日期时间格式 yyyyMMddHHmmss
     */
    TIME_PATTERN_SESSION("yyyyMMddHHmmss"),
    /**
     * 日期时间格式 yyyyMMddHHmmssSSS
     */
    TIME_PATTERN_MILLISECOND("yyyyMMddHHmmssSSS"),
    /**
     * 日期时间格式 yyyyMMdd
     */
    DATE_FMT_0("yyyyMMdd"),
    /**
     * 日期时间格式 yyyy/MM/dd
     */
    DATE_FMT_1("yyyy/MM/dd"),
    /**
     * 日期时间格式 yyyy/MM/dd hh:mm:ss
     */
    DATE_FMT_2("yyyy/MM/dd hh:mm:ss"),
    /**
     * 日期时间格式 yyyy-MM-dd
     */
    DATE_FMT_3("yyyy-MM-dd"),
    /**
     * 日期时间格式 yyyy年MM月dd日
     */
    DATE_FMT_4("yyyy年MM月dd日"),
    /**
     * 日期时间格式 yyyy-MM-dd HH
     */
    DATE_FMT_5("yyyy-MM-dd HH"),
    /**
     * 日期时间格式 yyyy-MM
     */
    DATE_FMT_6("yyyy-MM"),
    /**
     * 日期时间格式 MM月dd日 HH:mm
     */
    DATE_FMT_7("MM月dd日 HH:mm"),
    /**
     * 日期时间格式 HH:mm:ss
     */
    DATE_FMT_8("HH:mm:ss"),
    /**
     * 日期时间格式 yyyy.MM.dd
     */
    DATE_FMT_9("yyyy.MM.dd"),
    /**
     * 日期时间格式 HH:mm
     */
    DATE_FMT_10("HH:mm"),
    /**
     * 日期时间格式 yyyy.MM.dd HH:mm:ss
     */
    DATE_FMT_11("yyyy.MM.dd HH:mm:ss"),
    /**
     * 日期时间格式 MM月dd日
     */
    DATE_FMT_12("MM月dd日"),
    /**
     * 日期时间格式 yyyy年MM月dd日HH是mm分
     */
    DATE_FMT_13("yyyy年MM月dd日HH是mm分"),
    /**
     * 日期时间格式 yyyyMM
     */
    DATE_FMT_14("yyyyMM"),
    /**
     * 日期时间格式 MM-dd HH:mm:ss
     */
    DATE_FMT_15("MM-dd HH:mm:ss"),
    /**
     * 日期时间格式 yyyyMMddHHmm
     */
    DATE_FMT_16("yyyyMMddHHmm"),
    /**
     * 日期时间格式 HHmmss
     */
    DATE_FMT_17("HHmmss"),
    /**
     * 日期时间格式 yyyy
     */
    DATE_FMT_18("yyyy"),
    /**
     * 默认日期时间格式 yyyy-MM-dd HH:mm:ss
     */
    TIME_PATTERN("yyyy-MM-dd HH:mm:ss");


    /**
     * 时间单位
     */
    private int unit;
    /**
     * 格式化风格
     */
    private String pattern;
    FormatPattern(int unit) {
        this.unit = unit;
    }
    FormatPattern(String pattern) {
        this.pattern = pattern;
    }

    public int unit() {
        return this.unit;
    }
    public String pattern() {
        return this.pattern;
    }
}
