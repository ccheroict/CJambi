/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

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

    public static String toStringOrNull(Object obj) {
        if (obj == null) {
            return null;
        } else {
            return String.valueOf(obj);
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

    public static String toStringOrDefault(Double defaultValue, Double data) {
        if (data == null) {
            return toStringOrEmpty(defaultValue);
        } else {
            return toStringOrEmpty(data);
        }
    }

    public static String toStringOrDefault(Integer defaultValue, Integer data) {
        if (data == null) {
            return toStringOrEmpty(defaultValue);
        } else {
            return toStringOrEmpty(data);
        }
    }

    public static Date parseDate(String s) {
        try {
            return sdf.parse(s);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date lastChangedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Double parseDouble(String s) {
        s = s.replaceAll("\\s+", "");
        s = s.replace(',', '.');
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
