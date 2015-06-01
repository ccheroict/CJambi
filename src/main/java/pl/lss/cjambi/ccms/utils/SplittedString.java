/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

/**
 *
 * @author ctran
 */
public class SplittedString {

    public String first;
    public String last;

    public SplittedString(String s, String seperator) {
        int i = s.indexOf(seperator);
        if (i < 0) {
            first = s;
        } else {
            first = s.substring(0, i);
            last = s.substring(i + 1);
        }
    }
}
