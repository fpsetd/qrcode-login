package com.qrcode.login.util;

import java.math.BigDecimal;

public class NumberFormatUtils {

    private NumberFormatUtils() {
    }

    public static String formatInteger(int number) {
        if (number < 1000) {
            return String.valueOf(number);
        } else if (number < 10000) {
            return (number / 1000) + "," + String.valueOf(number).substring(1);
        } else if (number < 1_000_000) {
            return new BigDecimal(number).movePointLeft(3)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + " K";
        } else {
            return new BigDecimal(number).movePointLeft(4)
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + " W";
        }
    }
}
