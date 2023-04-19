package com.snackman.datnud11.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

@Service
@Slf4j
public class DateUtils {

    public Date formatDate(Date date){
        return stringToDate(dateToString(date));
    }

    public String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z's");
        dateFormat.setTimeZone(TimeZone.getTimeZone("VST"));
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
