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
public class DefaultConverter implements Converter<Object, Object> {

    @Override
    public Object toData(Object presentation) throws Exception {
        return presentation;
    }

    @Override
    public Object toPresentation(Object data) throws Exception {
        return data;
    }
}
