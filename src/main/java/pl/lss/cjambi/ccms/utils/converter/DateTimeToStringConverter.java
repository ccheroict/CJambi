/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

import java.util.Date;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class DateTimeToStringConverter implements Converter<Date, String> {

    @Override
    public Date toData(String presentation) throws Exception {
        return Utils.parseDate(presentation);
    }

    @Override
    public String toPresentation(Date data) {
        return Utils.toStringSimpleDate(data);
    }
}
