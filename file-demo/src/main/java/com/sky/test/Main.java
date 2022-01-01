package com.sky.test;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Main {
    private static int b = 0;
    public static void main(String[] args) throws ParseException {
        String yearMonth = "2021-08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse(yearMonth);
        DateTime firstDayOfMonth = DateUtil.beginOfMonth(date);
        int a = 0;

        int num = insertWeekData(firstDayOfMonth,1,yearMonth,1,a);
        System.out.println("b:"+b);
    }


    public static int insertWeekData(DateTime firstDay, Integer monthScoreId, String yearMonth, int weekIndex,int a){

        DateTime begin = null;
        DateTime end = null;
        int dayOfWeek = firstDay.dayOfWeek();
        // 第一天是周末
        if (firstDay.isWeekend()){
            DateTime nextWeek = DateUtil.offsetWeek(firstDay,1);
            DateTime nextMonDay = DateUtil.beginOfWeek(nextWeek, true);
            insertWeekData(nextMonDay,monthScoreId,yearMonth,weekIndex,a);
        }else if (dayOfWeek == 2){
            // 第一天是周一
            DateTime endOfMonth = DateUtil.endOfMonth(firstDay);
            DateTime endOfWeek = DateUtil.endOfWeek(firstDay);
            begin = firstDay;
            // end - begin
            // >=0 本月在本周结束
            // <0  本月未结束
            long between = DateUtil.between(endOfMonth,endOfWeek, DateUnit.DAY, false);
            if (between>=0){
                end = endOfMonth;
            }else {
                end = endOfWeek;
                insertWeekData(DateUtil.offsetWeek(firstDay,1),monthScoreId,yearMonth,weekIndex+1,a);
            }
        }else if (dayOfWeek > 2 && dayOfWeek < 7){
            // 第一天是 周二至周五的某一天
            begin = firstDay;
            end = DateUtil.endOfWeek(firstDay,true);
            DateTime nextWeek = DateUtil.offsetWeek(firstDay,1);
            DateTime nextMonDay = DateUtil.beginOfWeek(nextWeek, true);
            insertWeekData(nextMonDay,monthScoreId,yearMonth,weekIndex+1,a);
        }

        if (ObjectUtil.isNotEmpty(begin)){
            // 用 本周第一天 来判断本周在月中是第几周
            int weekOfMonth = firstDay.weekOfMonth();
            int weekOfYear = firstDay.weekOfYear();
            LocalDate beginLocalDate = begin.toInstant().atZone((ZoneId.systemDefault())).toLocalDate();
            LocalDate endLocalDate = end.toInstant().atZone((ZoneId.systemDefault())).toLocalDate();

            AssessWeekScore weekScore = new AssessWeekScore();
            weekScore.setMonthScoreId(monthScoreId);
            weekScore.setYearMonthDate(yearMonth);
            weekScore.setMonthWeek(weekIndex);
            weekScore.setYearWeek(weekOfYear);
            weekScore.setStartDate(beginLocalDate);
            weekScore.setEndDate(endLocalDate);
            System.out.println(weekScore);
            b++;
        }
        return weekIndex;
    }

    public static int getWeekNum(DateTime firstDay, Integer monthScoreId, String yearMonth, int weekIndex){
        DateTime begin = null;
        DateTime end = null;
        int dayOfWeek = firstDay.dayOfWeek();
        // 第一天是周末
        if (firstDay.isWeekend()){
            DateTime nextWeek = DateUtil.offsetWeek(firstDay,1);
            DateTime nextMonDay = DateUtil.beginOfWeek(nextWeek, true);
            getWeekNum(nextMonDay,monthScoreId,yearMonth,weekIndex);
        }else if (dayOfWeek == 2){
            // 第一天是周一
            DateTime endOfMonth = DateUtil.endOfMonth(firstDay);
            DateTime endOfWeek = DateUtil.endOfWeek(firstDay);
            begin = firstDay;
            // end - begin
            // >=0 本月在本周结束
            // <0  本月未结束
            long between = DateUtil.between(endOfMonth,endOfWeek, DateUnit.DAY, false);
            if (between>=0){
                end = endOfMonth;
            }else {
                end = endOfWeek;
                getWeekNum(DateUtil.offsetWeek(firstDay,1),monthScoreId,yearMonth,weekIndex+1);
            }
        }else if (dayOfWeek > 2 && dayOfWeek < 7){
            // 第一天是 周二至周五的某一天
            begin = firstDay;
            end = DateUtil.endOfWeek(firstDay,true);
            DateTime nextWeek = DateUtil.offsetWeek(firstDay,1);
            DateTime nextMonDay = DateUtil.beginOfWeek(nextWeek, true);
            getWeekNum(nextMonDay,monthScoreId,yearMonth,weekIndex+1);
        }

        
        return -1;
    }
}
