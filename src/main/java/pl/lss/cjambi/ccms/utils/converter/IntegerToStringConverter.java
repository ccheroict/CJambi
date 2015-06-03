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
public class IntegerToStringConverter extends TypeToStringConverter<Integer> {

    private Integer defaultValue;

    public IntegerToStringConverter() {
        this(null);
    }

    public IntegerToStringConverter(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Integer toData(String presentation) throws NumberFormatException {
        try {
            return Integer.parseInt(presentation.trim());
        } catch (NumberFormatException ex) {
            throw ex;
        }
    }

    @Override
    public String toPresentation(Integer data) {
        return Utils.toStringOrDefault(defaultValue, data);
    }
}
