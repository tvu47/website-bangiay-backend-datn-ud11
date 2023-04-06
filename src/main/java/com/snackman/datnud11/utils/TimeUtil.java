package com.snackman.datnud11.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String formatTime(Date time, String format){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(time);
        }catch (Exception e){
            return null;
        }
    }

    public static Date strToDate(String time, String format){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(time);
        }
        catch (Exception e){
            return null;
        }
    }

}
