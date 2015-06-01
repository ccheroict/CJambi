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
public class DateToStringConverter extends TypeToStringConverter<Date> {

    @Override
    public Date toData(String presentation) {
        Date date = Utils.parseDate(presentation);
        return Utils.getStartOfDay(date);
    }

    @Override
    public String toPresentation(Date data) {
        return Utils.toStringOrEmpty(Utils.getStartOfDay(data));
    }
}
