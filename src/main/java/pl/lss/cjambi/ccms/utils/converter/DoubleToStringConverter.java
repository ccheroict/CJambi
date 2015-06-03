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
public class DoubleToStringConverter extends TypeToStringConverter<Double> {

    private Double defaultValue;

    public DoubleToStringConverter() {
        this(null);
    }

    public DoubleToStringConverter(Double defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Double toData(String presentation) throws NumberFormatException {
        try {
            return Utils.parseDouble(presentation);
        } catch (NumberFormatException ex) {
            throw ex;
        }
    }

    @Override
    public String toPresentation(Double data) {
        return Utils.toStringOrDefault(defaultValue, data);
    }
}
