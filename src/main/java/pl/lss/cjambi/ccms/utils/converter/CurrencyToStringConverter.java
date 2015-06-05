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
        this(null, symbol);
    }

    public CurrencyToStringConverter(Double defaultValue, String symbol) {
        super(defaultValue);
        this.symbol = symbol;
    }

    @Override
    public Double toData(String presentation) throws NumberFormatException {
        int i = presentation.indexOf(symbol);
        if (i >= 0) {
            presentation = presentation.substring(0, i).trim() + presentation.substring(i + symbol.length()).trim();
        }
        return super.toData(presentation);
    }

    @Override
    public String toPresentation(Double data) {
        return Utils.toStringOrDefaultIfNull(defaultValue, data) + " " + symbol;
    }
}
