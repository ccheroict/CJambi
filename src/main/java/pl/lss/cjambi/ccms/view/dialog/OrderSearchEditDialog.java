/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.utils.converter.DateToQDateConverter;
import pl.lss.cjambi.ccms.view.widget.DateEdit;

/**
 *
 * @author ctran
 */
public class OrderSearchEditDialog extends FilterDialog {

//    private SuggestBox code;
    private DateEdit dateFrom, dateTo;

    public OrderSearchEditDialog() {
        super();
//        code = new SuggestBox(Order.class, Order.CODE_FIELD) {
//
//            @Override
//            public List fetchData() {
//                Filter filter = new Filter();
//                filter.productCode = text();
//                return db.getProduct(filter);
//            }
//        };
        dateFrom = new DateEdit();
        dateTo = new DateEdit();

        build();
    }

    @Override
    protected QWidget buildContent() {
        QWidget widget = new QWidget();
        QFormLayout layout = new QFormLayout(widget);
//        layout.addRow(new QLabel(I18n.orderCode), code);
        layout.addRow(new QLabel(I18n.from), dateFrom);
        layout.addRow(new QLabel(I18n.to), dateTo);

//        editor.addMapping(code, Filter.ORDER_CODE_FIELD);
        editor.addMapping(dateFrom, Filter.DATE_FROM_FIELD, new DateToQDateConverter());
        editor.addMapping(dateTo, Filter.ORDER_DATE_TO_FIELD, new DateToQDateConverter());
        return widget;
    }

    @Override
    protected void onOkBtnClicked() throws Exception {
        try {
            super.onOkBtnClicked();
        } catch (Exception ex) {
        }
        close();
    }

    @Override
    protected void customFillBeanAfterCommit() throws Exception {
        bean.dateFrom = Utils.getStartOfDay(bean.dateFrom);
        bean.dateTo = Utils.getEndOfDay(bean.dateTo);
    }
}
