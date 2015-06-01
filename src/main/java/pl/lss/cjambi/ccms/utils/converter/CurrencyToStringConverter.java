/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class CurrencyToStringConverter extends DoubleToStringConverter {

    private String symbol;

    public CurrencyToStringConverter(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Double toData(String presentation) {
        int i = presentation.indexOf(symbol);
        if (i < 0) {
            return null;
        }
        presentation = presentation.substring(0, i) + presentation.substring(i + symbol.length());
        return super.toData(presentation);
    }

    @Override
    public String toPresentation(Double data) {
        return Utils.toStringOrDefault(0.0, data) + " " + symbol;
    }

    public static void main(String[] args) {
        CurrencyToStringConverter test = new CurrencyToStringConverter("zł");
        System.out.println(test.toData("2.336zł"));
        System.out.println(test.toData("2.336 zł"));
        System.out.println(test.toData(" 2.336zł"));
        System.out.println(test.toData(" 2.336  zł"));
        System.out.println(test.toData(" 222 2.336zł"));
        System.out.println(test.toData(" 222 2,336zł"));
        System.out.println(test.toPresentation(2.338));
    }
}
