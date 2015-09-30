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
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.converter.IntegerToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.SupplierToCodeConverter;
import static pl.lss.cjambi.ccms.view.dialog.BeanEditDialog.db;
import pl.lss.cjambi.ccms.view.widget.LineEdit;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class ProductSearchEditDialog extends FilterDialog {

    private SuggestBox code;
    private LineEdit id;
    private SuggestBox supplier;

    public ProductSearchEditDialog() {
        super();
        id = new LineEdit();
        supplier = new SuggestBox(Supplier.class, Supplier.CODE_FIELD) {

            @Override
            public List fetchData() {
                Filter filter = new Filter();
                filter.supplierCode = text();
                return db.getSupplier(filter);
            }
        };

        code = new SuggestBox(Product.class, Product.CODE_FIELD) {

            @Override
            public List fetchData() {
                Filter filter = new Filter();
                filter.productCode = text();
                return db.getProduct(filter);
            }
        };

        build();

    }

    @Override
    protected QWidget buildContent() {
        QWidget widget = new QWidget();
        QFormLayout layout = new QFormLayout(widget);
        layout.addRow(new QLabel(I18n.ID), id);
        layout.addRow(new QLabel(I18n.supplierCode), supplier);
        layout.addRow(new QLabel(I18n.productCode), code);

        editor.addMapping(id, Filter.ID_FIELD, new IntegerToStringConverter());
        editor.addMapping(supplier, Filter.SUPPLIER_FIELD, new SupplierToCodeConverter());
        editor.addMapping(code, Filter.PRODUCT_CODE_FIELD);

        return widget;
    }

    @Override
    protected void onOkBtnClicked() throws Exception {
        try {
            super.onOkBtnClicked();
        } catch (Exception ex) { //To change body of generated methods, choose Tools | Templates.
        }
        close();
    }
}
