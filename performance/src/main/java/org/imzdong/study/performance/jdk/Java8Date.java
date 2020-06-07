package org.imzdong.study.performance.jdk;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ValueRange;

/**
 * @description: java8时间使用Demo
 * @author: Winter
 * @time: 2020/2/17
 */
public class Java8Date {

    public static void main(String[] args)  {
        //1、时间线 Instant
        //testInstant();
        //2、本地时间LocalDate LocalTime
        //testLocalDateAndTime();
        //3、日期工具类
        //testTemporalAdjusters();
        //4、日期转换
        //testFormatter();
        /*int year = 2020;
        int month = 12;
        LocalDate localDate = LocalDate.of(year, month, 1);
        System.out.println(LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("YYYYMM")));*/

        //between(202001,202103);
        Integer start = 20200401;
        Integer end = 20200407;
        //period(start,end,1);
        //period(start,end,2);
        //period(start,end,3);
        Integer[] prePeriod = prePeriod(start, end);
        prePeriod = prePeriod(prePeriod[0], prePeriod[1]);
        prePeriod = prePeriod(prePeriod[0], prePeriod[1]);
    }

    /**
     * 获取上一个周期区间
     * @param start
     * @param end
     */
    private static Integer[] prePeriod(Integer start,Integer end){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(start.toString(),formatter);
        LocalDate endDate = LocalDate.parse(end.toString(),formatter);
        Period between = Period.between(startDate, endDate);
        LocalDate plusEndDay = startDate.minusDays(1);
        LocalDate plusStartDay = plusEndDay.minusDays(between.getDays());
        System.out.println(plusStartDay+"~"+plusEndDay);
        System.out.println("----------------");
        return new Integer[]{Integer.parseInt(plusStartDay.format(formatter)),Integer.parseInt(plusEndDay.format(formatter))};
    }

    /**
     * 获取上一个周期区间
     * @param start
     * @param end
     * @param period
     */
    private static void period(Integer start,Integer end, int period){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(start.toString(),formatter);
        LocalDate endDate = LocalDate.parse(end.toString(),formatter);
        Period between = Period.between(startDate, endDate);
        int startPeriod = (period-1)*between.getDays()+period;
        LocalDate plusEndDay = startDate.minusDays(startPeriod);
        LocalDate plusStartDay = plusEndDay.minusDays(between.getDays());
        System.out.println(plusStartDay+"~"+plusEndDay);
        System.out.println("----------------");
    }

    private static void between(Integer start, Integer end){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth startLocalDate = YearMonth.parse(start.toString(),formatter);
        YearMonth endLocalDate = YearMonth.parse(end.toString(),formatter);
        //Duration between = Duration.between(startLocalDate, endLocalDate);
        System.out.println(startLocalDate);
        System.out.println(endLocalDate);
        //System.out.println(between);
        //int months = between.
        long until = startLocalDate.until(endLocalDate, ChronoUnit.MONTHS);
        for(int num=0;num <= until;num++){
            System.out.println(startLocalDate.plusMonths(num));
        }
       /* while (num<months) {
            LocalDate localDate = startLocalDate.plusMonths(num++);
            System.out.println(localDate);
        }*/
    }

    private static void testFormatter(){
        String yearMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("YYYYMM"));
        System.out.println("YYYYMM yearMonth: "+yearMonth);
        YearMonth yearMonth2 = YearMonth.now().minusMonths(1);
        System.out.println("YearMonth yearMonth2: "+yearMonth2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth parse = YearMonth.parse(yearMonth,formatter);
        System.out.println("YYYYMM YearMonth parse:"+parse);
        System.out.println("YYYYMM YearMonth parse.getYear(): "+parse.getYear());
        System.out.println("YYYYMM YearMonth parse.getMonth(): "+parse.getMonth());
        System.out.println("YYYYMM YearMonth parse.getMonthValue(): "+parse.getMonthValue());
    }

    /**
     * 日期调整器测试
     * 2020-03-01
     * nextWorDayDate:2020-02-18
     */
    private static void testTemporalAdjusters() {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println(localDate);

        //自定义调整器
        TemporalAdjuster nextWorDay = TemporalAdjusters.ofDateAdjuster(w -> {
            LocalDate result = w;
            do {
                result = result.plusDays(1);
            }
            while (result.getDayOfWeek().getValue() >= 6);
            return result;
        });
        LocalDate nextWorDayDate = LocalDate.now().with(nextWorDay);
        System.out.println("nextWorDayDate:"+nextWorDayDate );
    }

    /**
     * 测试本地时间
     * LocalDate 本地日期
     * LocalTime 本地时刻
     * Period 本地时间之间的间隔
     * LocalDateTime
     * ------------------------------
     * 执行结果：
     * LocalDate.now() today:2020-02-17
     * LocalDate.of() oneDay:2019-02-28
     * LocalDate.plusDays() plusMonths:2020-02-29
     * Period until:P11M20D
     * Period until.getYears():0
     * Period until.getMonths():11
     * Period until.getDays():20
     * Period until.getChronology():ISO
     * Period until.getUnits():[Years, Months, Days]
     * LocalTime localTime:15:59:55.345
     * LocalTime of:22:30
     * LocalDateTime localDateTime:2020-02-17T15:59:55.346
     */
    private static void testLocalDateAndTime(){
        //1、LocalDate
        LocalDate today = LocalDate.now();
        System.out.println("LocalDate.now() today:"+today);
        LocalDate oneDay = LocalDate.of(2019,2,28);//日期格式不对会报错
        System.out.println("LocalDate.of() oneDay:"+oneDay);
        LocalDate plusMonths = LocalDate.of(2020,1,31).plusMonths(1);//新增一个月会得到正确的日期而不是2月31
        System.out.println("LocalDate.plusDays() plusMonths:"+plusMonths);
        //2、Period
        //Period until = today.until(oneDay);
        Period until = oneDay.until(today);//小日期在前，大日期在后
        System.out.println("Period until:"+until);
        System.out.println("Period until.getYears():"+until.getYears());
        System.out.println("Period until.getMonths():"+until.getMonths());
        System.out.println("Period until.getDays():"+until.getDays());
        System.out.println("Period until.getChronology():"+until.getChronology());
        System.out.println("Period until.getUnits():"+until.getUnits());
        //3、LocalTime
        LocalTime localTime = LocalTime.now();
        LocalTime of = LocalTime.of(22, 30);//时分秒毫秒
        System.out.println("LocalTime localTime:"+localTime);
        System.out.println("LocalTime of:"+of);
        //4、LocatDateTime
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("LocalDateTime localDateTime:"+localDateTime);

    }

    /**
     * 测试时间线
     * Instant：时间线，相当于java.util的Date
     * Duration：计算两个“时间”的间隔
     * -------------------------------
     * 执行结果
     * Instant start:2020-02-17T07:16:14.854Z
     * Duration between:PT0.3S
     * nextInt:100
     * between:300
     * between2:300
     * negative:false
     */
    private static void testInstant(){
        Instant start = Instant.now();
        System.out.println("Instant start:"+start);
        int nextInt = 100;
        try {
            Thread.sleep(nextInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant end = Instant.now();
        Duration between = Duration.between(start, end);
        System.out.println("Duration between:"+between);
        long toMillis = between.toMillis();
        System.out.println("nextInt:"+nextInt);
        System.out.println("between:"+toMillis);
        //比较两个时间段的大小
        Instant end2 = Instant.now();
        Duration between2 = Duration.between(start, end2);
        System.out.println("between2:"+between2.toMillis());
        //between*10-between2>0
        boolean negative = between.multipliedBy(10).minus(between2).isNegative();
        System.out.println("negative:"+negative);
    }
}
