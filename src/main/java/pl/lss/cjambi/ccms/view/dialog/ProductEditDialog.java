/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import java.util.List;
import pl.lss.cjambi.ccms.bean.DiscountType;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.bean.Tax;
import pl.lss.cjambi.ccms.bean.Unit;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.view.widget.ComboBox;
import pl.lss.cjambi.ccms.view.widget.Label;
import pl.lss.cjambi.ccms.view.widget.LineEdit;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class ProductEditDialog extends BeanEditDialog<Product> {

    private static final ProductEditDialog instance = new ProductEditDialog();

    public static ProductEditDialog getInstance() {
        return instance;
    }

    private SuggestBox<Product> code;
    private SuggestBox<Supplier> supplier;
    private LineEdit size, colour, packSize, defaultPrice, discountValue;
    private ComboBox<DiscountType> discountType;
    private ComboBox<Tax> tax;
    private ComboBox<Unit> unit;
    private Label finalPrice;

    public ProductEditDialog() {
        super();
        code = new SuggestBox(Product.class, Product.CODE_FIELD) {

            @Override
            public List fetchData() {
                Filter filter = new Filter();
                filter.productCode = text();
                return db.getProduct(filter);
            }
        };
        supplier = new SuggestBox(Supplier.class, Supplier.CODE_FIELD) {

            @Override
            public List fetchData() {
                Filter filter = new Filter();
                filter.supplierCode = text();
                return db.getSupplier(filter);
            }
        };
        size = new LineEdit();
        colour = new LineEdit();
        packSize = new LineEdit();
        defaultPrice = new LineEdit();
        discountValue = new LineEdit();
        discountType = new ComboBox(db.getDiscountType(), DiscountType.NAME_FIELD);
        tax = new ComboBox(db.getTax(), Tax.NAME_FIELD);
        unit = new ComboBox(db.getUnit(), Unit.NAME_FIELD);
        finalPrice = new Label();

        build();
    }

    @Override
    protected QIcon getDialogIcon() {
        return IconResources.APP_ICON;
    }

    @Override
    protected String getDialogTitle() {
        return I18n.productInfo;
    }

    @Override
    protected QWidget buildContent() {
        QWidget widget = new QWidget();
        QGridLayout grid = new QGridLayout();
        int nRow = 0;
        grid.addWidget(new QLabel(I18n.productCode), nRow, 0);
        grid.addWidget(code, nRow++, 1);
        grid.addWidget(new QLabel(I18n.supplierCode), nRow, 0);
        grid.addWidget(supplier, nRow++, 1);
        grid.addWidget(new QLabel(I18n.size), nRow, 0);
        grid.addWidget(size, nRow++, 1);
        grid.addWidget(new QLabel(I18n.colour), nRow, 0);
        grid.addWidget(colour, nRow++, 1);
        grid.addWidget(new QLabel(I18n.packSize), nRow, 0);
        grid.addWidget(packSize, nRow, 1);
        grid.addWidget(unit, nRow++, 2);
        grid.addWidget(new QLabel(I18n.defaultPrice), nRow, 0);
        grid.addWidget(defaultPrice, nRow++, 1);
        grid.addWidget(new QLabel(I18n.discountValue), nRow, 0);
        grid.addWidget(discountValue, nRow, 1);
        grid.addWidget(discountType, nRow++, 2);
        grid.addWidget(new QLabel(I18n.finalPrice), nRow, 0);
        grid.addWidget(finalPrice, nRow++, 1);

        return widget;
    }

    @Override
    protected void customFillBeforeExec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
