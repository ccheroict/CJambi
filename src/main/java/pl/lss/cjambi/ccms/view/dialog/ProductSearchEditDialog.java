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
import pl.lss.ccms.cjambi.bean.Product;
import pl.lss.cjambi.ccms.resources.I18n;
import static pl.lss.cjambi.ccms.view.dialog.BeanEditDialog.db;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class ProductSearchEditDialog extends FilterDialog {

    private SuggestBox code;

    public ProductSearchEditDialog() {
        super();
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
        layout.addRow(new QLabel(I18n.productCode), code);

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
