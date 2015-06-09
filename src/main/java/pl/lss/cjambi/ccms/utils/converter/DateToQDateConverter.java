/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

import com.trolltech.qt.core.QDate;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ctran
 */
public class DateToQDateConverter implements Converter<Date, QDate> {

    @Override
    public Date toData(QDate date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.year(), date.month(), date.day());
        return calendar.getTime();
    }

    @Override
    public QDate toPresentation(Date day) throws Exception {
        QDate date = new QDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        date.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        return date;
    }
}
