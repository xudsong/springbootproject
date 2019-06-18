package com.xudasong.service.springcloudservice.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 时间工具类
 * Java8的DateTimeFormatter是线程安全的，而SimpleDateFormat并不是线程安全
 */
public class DateUtils {

    /**
     * 获取当前时间
     * @param pattern 格式：如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowTime(String pattern){
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return today.format(formatter);
    }

    public static void main(String[] args)throws Exception{
        String pattern = "yyyy-MM-dd HH:mm:ss";
        System.out.println(DateUtils.getNowTime(pattern));

        // 获取今天的日期
        LocalDate today = LocalDate.now();
        // 今天是几号
        int dayofMonth = today.getDayOfMonth();
        // 今天是周几（返回的是个枚举类型，需要再getValue()）
        int dayofWeek = today.getDayOfWeek().getValue();
        // 今年是哪一年
        int dayofYear = today.getDayOfYear();

        // 根据字符串取： 严格按照yyyy-MM-dd验证，02写成2都不行，当然也有一个重载方法允许自己定义格式
        LocalDate endOfFeb = LocalDate.parse("2018-02-28");


        // 取本月第1天：
        LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth()); // 2018-04-01
        // 取本月第2天：
        LocalDate secondDayOfThisMonth = today.withDayOfMonth(2); // 2018-04-02
        // 取本月最后一天，再也不用计算是28，29，30还是31：
        LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth()); // 2018-04-30
        // 取下一天：
        LocalDate firstDayOfNextMonth = lastDayOfThisMonth.plusDays(1); // 变成了2018-05-01
        // 取2017年1月第一个周一：
        LocalDate firstMondayOf2017 = LocalDate.parse("2017-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)); // 2017-01-02

    }

}
