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
public abstract class TypeToStringConverter<DATA> implements Converter<DATA, String> {

    @Override
    public String toPresentation(DATA data) {
        return Utils.toStringOrEmpty(data);
    }
}
