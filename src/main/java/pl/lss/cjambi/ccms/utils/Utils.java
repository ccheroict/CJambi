/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.ccms.cjambi.bean.DiscountType;

/**
 *
 * @author ctran
 */
public class Utils {

    private static final Utils instance = new Utils();
    private static final Logger logger = Logger.getLogger(Utils.class);
    private static final DecimalFormat doubleFormatter = new DecimalFormat();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    static {
        doubleFormatter.setMinimumFractionDigits(2);
        doubleFormatter.setMaximumFractionDigits(2);
    }

    public static String toStringOrEmpty(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    public static String toStringOrDefaultIfNull(Object defaultValue, Object data) {
        if (data == null) {
            return toStringOrEmpty(defaultValue);
        } else {
            return toStringOrEmpty(data);
        }
    }

    public static List<String> splitToArray(String cmd, String seperator) {
        List<String> res = new ArrayList<>();
        SplittedString ss;
        while (cmd != null) {
            ss = new SplittedString(cmd, seperator);
            res.add(ss.first);
            cmd = ss.last;
        }
        return res;
    }

    public static String toStringSimpleDate(Date date) {
        return sdf.format(date);
    }

    public static Date parseDate(String s) throws ParseException {
        return sdf.parse(s);
    }

    public static Double parseDoubleOrDefaultIfEmpty(Double defaultValue, String s) throws NumberFormatException {
        if (s == null || s.isEmpty()) {
            return defaultValue;
        }
        s = s.replaceAll("\\s+", "");
        s = s.replace(',', '.');
        return Double.parseDouble(s);
    }

    public static Integer parseIntOrDefaultIfEmpty(Integer defaultValue, String s) throws NumberFormatException {
        if (s == null || s.isEmpty()) {
            return defaultValue;
        }
        return Integer.parseInt(s);
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Double getValueWithDiscount(Double value, Double discountValue, DiscountType discountType) {
        if (discountValue != null && discountValue != 0) {
            if (discountType.name.equals("%")) {
                value = (1 - discountValue / 100.0) * value;
            } else {
                value -= discountValue;
            }
        }
        return value;
    }

    public static String toUTF8OrEmpty(Object value) {
        byte[] data = toStringOrEmpty(value).getBytes();
        String res = new String(data, Charset.forName("UTF-8"));
        return res;
    }
}
