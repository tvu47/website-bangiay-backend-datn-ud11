package com.snackman.datnud11.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtil {

    public static String formatNumberVN(Double number) {
        Locale localeEN = new Locale("vi", "VI");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        return en.format(number);
    }
}
