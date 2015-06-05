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
public class DoubleToStringConverter implements Converter<Double, String> {

    protected Double defaultValue;

    public DoubleToStringConverter() {
        this(null);
    }

    public DoubleToStringConverter(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Double toData(String presentation) throws NumberFormatException {
        try {
            return Utils.parseDoubleOrDefaultIfEmpty(defaultValue, presentation.trim());
        } catch (NumberFormatException ex) {
            throw ex;
        }
    }

    @Override
    public String toPresentation(Double data) {
        return Utils.toStringOrDefaultIfNull(defaultValue, data);
    }
}
