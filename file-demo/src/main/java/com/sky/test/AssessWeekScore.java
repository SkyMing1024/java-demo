package com.sky.test;

import java.time.LocalDate;

public class AssessWeekScore {

    private Integer monthScoreId;
    private String yearMonthDate;
    private int monthWeek;
    private int yearWeek;
    private LocalDate StartDate;
    private LocalDate endDate;

    public Integer getMonthScoreId() {
        return monthScoreId;
    }

    public void setMonthScoreId(Integer monthScoreId) {
        this.monthScoreId = monthScoreId;
    }

    public String getYearMonthDate() {
        return yearMonthDate;
    }

    public void setYearMonthDate(String yearMonthDate) {
        this.yearMonthDate = yearMonthDate;
    }

    public int getMonthWeek() {
        return monthWeek;
    }

    public void setMonthWeek(int monthWeek) {
        this.monthWeek = monthWeek;
    }

    public int getYearWeek() {
        return yearWeek;
    }

    public void setYearWeek(int yearWeek) {
        this.yearWeek = yearWeek;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "AssessWeekScore{" +
                "monthScoreId=" + monthScoreId +
                ", yearMonthDate='" + yearMonthDate + '\'' +
                ", monthWeek=" + monthWeek +
                ", yearWeek=" + yearWeek +
                ", StartDate=" + StartDate +
                ", endDate=" + endDate +
                '}';
    }
}
