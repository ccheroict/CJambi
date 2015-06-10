/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import java.util.List;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.ccms.cjambi.bean.Supplier;
import pl.lss.cjambi.ccms.resources.I18n;
import static pl.lss.cjambi.ccms.view.dialog.BeanEditDialog.db;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class SupplierSearchEditDialog extends FilterDialog {

    private SuggestBox supplier;

    public SupplierSearchEditDialog() {
        super();
        supplier = new SuggestBox(Supplier.class, Supplier.CODE_FIELD) {

            @Override
            public List fetchData() {
                Filter filter = new Filter();
                filter.supplierCode = text();
                return db.getSupplier(filter);
            }
        };

        build();
    }

    @Override
    protected QWidget buildContent() {
        QWidget widget = new QWidget();
        QFormLayout layout = new QFormLayout(widget);
        layout.addRow(new QLabel(I18n.supplierCode), supplier);

        editor.addMapping(supplier, Filter.SUPPLIER_CODE_FIELD);
        return widget;
    }

    @Override
    protected void onOkBtnClicked() {
        try {
            super.onOkBtnClicked();
        } catch (Exception ex) {
        }
        close();
    }
}
