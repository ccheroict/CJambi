/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import java.util.List;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.utils.Styles;
import pl.lss.cjambi.ccms.utils.converter.SupplierToCodeConverter;
import pl.lss.cjambi.ccms.view.widget.LineEdit;
import pl.lss.cjambi.ccms.view.widget.PlainTextEdit;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class SupplierEditDialog extends BeanEditDialog<Supplier> {

    private SuggestBox code;
    private LineEdit name;
    private PlainTextEdit note;

    public SupplierEditDialog(Supplier supplier) {
        super();
        setBean(supplier);
        code = new SuggestBox<Supplier>(Supplier.class, Supplier.CODE_FIELD) {

            @Override
            public List<Supplier> fetchData() {
                Filter filter = new Filter();
                filter.supplierCode = text();
                return db.getSupplier(filter);
            }
        };
        name = new LineEdit();
        note = new PlainTextEdit();

        build();
    }

    @Override
    protected QIcon getDialogIcon() {
        return IconResources.APP_ICON;
    }

    @Override
    protected String getDialogTitle() {
        return I18n.supplierInfo;
    }

    @Override
    protected QWidget buildContent() {
        QWidget widget = new QWidget();
        QFormLayout form = new QFormLayout(widget);
        form.addRow(new QLabel(I18n.supplierCode), code);
        form.addRow(new QLabel(I18n.supplierName), name);
        form.addRow(new QLabel(I18n.note), note);

        editor.addMapping(code, Supplier.SELF, new SupplierToCodeConverter());
        editor.addMapping(name, Supplier.NAME_FIELD);
        editor.addMapping(note, Supplier.NOTE_FIELD);

        return widget;
    }

    @Override
    protected void onOkBtnClicked() {
        try {
            super.onOkBtnClicked(); //To change body of generated methods, choose Tools | Templates.
            db.createOrUpdateSupplier(bean);
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
            return;
        }
        close();
    }

    @Override
    protected boolean validate() {
        return setStyleSheet(code, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(code) && stateIsNullOrItself(code, bean));
    }

    @Override
    protected void customFillBeanAfterCommit() {
        bean.code = code.text();
        bean.company = Cache.getUserCompany();
    }
}
