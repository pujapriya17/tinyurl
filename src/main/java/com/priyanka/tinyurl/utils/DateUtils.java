package com.priyanka.tinyurl.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * Adds the specified amount of time to a given date.
     * @param referenceDate The date to which we need to add the specified time.
     * @param units No. of units of time to be added to referenceDate
     * @param durationType Type of duration - specified by one of the enums provided by {@link Calendar} e.g. {@link Calendar#YEAR}.
     * @return The {@param referenceDate} pushed forward by the specified amount of time.
     */
    public static Date addToDate(Date referenceDate, int units, int durationType){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(referenceDate);
        calendar.add(durationType, units);
        return calendar.getTime();
    }
}
