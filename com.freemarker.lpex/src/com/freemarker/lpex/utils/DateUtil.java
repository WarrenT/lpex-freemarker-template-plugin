package com.freemarker.lpex.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    private DateUtil() {
    }

    /**
     * Returns the time difference between two given dates measured in hours.
     * 
     * @param aDate1
     * @param aDate2
     * @return hours between the dates
     */
    public static long getTimeDifference(Date aDate1, Date aDate2) {
        Calendar tCalendar1 = Calendar.getInstance();
        tCalendar1.setTime(aDate1);

        Calendar tCalendar2 = Calendar.getInstance();
        tCalendar2.setTime(aDate2);

        long tDiff = tCalendar1.getTimeInMillis() - tCalendar2.getTimeInMillis();
        long tDiffHours = Math.abs(tDiff / (60 * 60 * 1000));

        return tDiffHours;
    }

}
