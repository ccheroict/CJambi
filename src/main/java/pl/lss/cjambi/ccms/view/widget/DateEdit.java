/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.core.QDate;
import com.trolltech.qt.gui.QCalendarWidget;
import com.trolltech.qt.gui.QDateEdit;

/**
 *
 * @author ctran
 */
public class DateEdit extends QDateEdit implements HasState {

    private QCalendarWidget calenderWidget = new QCalendarWidget();

    public DateEdit() {
        setCalendarPopup(true);
        setCalendarWidget(calenderWidget);
    }

    @Override
    public Object getState() {
        return date();
    }

    @Override
    public void setState(Object obj) {
        setDate((QDate) obj);
    }
}
