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

    @Override
    public Double toData(String presentation) {
        try {
            return Utils.parseDouble(presentation);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
