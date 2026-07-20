package com.swapi.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtils {
	
	public static String MINUTE = "MINUTE";
	public static String HOUR = "HOUR";
	public static String DAY = "DAY";
	public static String WEEK = "WEEK";
	public static String MONTH = "MONTH";
	public static String YEAR = "YEAR";

	public static long timeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();
        long weeks = days / 7;
        long months = days / 30;
        long years = days / 365;

        if (minutes < 60) {
            return minutes;
        }

        if (hours < 24) {
            return hours;
        }

        if (days < 7) {
            return days;
        }

        if (weeks < 4) {
            return weeks;
        }

        if (months < 12) {
            return months;
        }

        return years;
    }
	
	public static String timeAgoUOM(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();
        long weeks = days / 7;
        long months = days / 30;

        if (minutes < 60) {
            return MINUTE;
        }

        if (hours < 24) {
            return HOUR;
        }

        if (days < 7) {
            return DAY;
        }

        if (weeks < 4) {
            return WEEK;
        }

        if (months < 12) {
            return MONTH;
        }

        return YEAR;
    }

}
