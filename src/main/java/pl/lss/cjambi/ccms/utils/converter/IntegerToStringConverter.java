/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

/**
 *
 * @author ctran
 */
public class IntegerToStringConverter extends TypeToStringConverter<Integer> {

    @Override
    public Integer toData(String presentation) {
        try {
            return Integer.parseInt(presentation);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
