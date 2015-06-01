/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ctran
 */
public class StringUtils {

    private static final char SPACE_CHAR = ' ';

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

    public static String removeUnnecessarySpace(String s) {
        s = s.trim();
        StringBuilder sb = new StringBuilder();
        char lastChar = SPACE_CHAR;
        for (int i = 0; i < s.length(); i++) {
            if (lastChar == SPACE_CHAR && s.charAt(i) == SPACE_CHAR) {
                continue;
            }
            lastChar = s.charAt(i);
            sb.append(lastChar);
        }
        return sb.toString();
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
}
