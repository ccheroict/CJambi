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
public interface Converter<DATA, PRESENTATION> {

    public DATA toData(PRESENTATION presentation);

    public PRESENTATION toPresentation(DATA data);
}
