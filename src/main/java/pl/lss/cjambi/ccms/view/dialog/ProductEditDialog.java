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
import pl.lss.cjambi.ccms.bean.Catalog;
import pl.lss.cjambi.ccms.bean.DiscountType;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.bean.Tax;
import pl.lss.cjambi.ccms.bean.Unit;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.utils.Constants;
import pl.lss.cjambi.ccms.utils.Styles;
import pl.lss.cjambi.ccms.utils.converter.CurrencyToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.DoubleToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.IntegerToStringConverter;
import pl.lss.cjambi.ccms.view.widget.ComboBox;
import pl.lss.cjambi.ccms.view.widget.Label;
import pl.lss.cjambi.ccms.view.widget.LineEdit;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class ProductEditDialog extends BeanEditDialog<Product> {

//    private static final ProductEditDialog instance = new ProductEditDialog();
//    public static ProductEditDialog getInstance() {
//        return instance;
//    }
    private SuggestBox<Product> code;
    private SuggestBox<Supplier> supplier;
    private LineEdit size, colour, packSize, originalPrice, discountValue;
    private ComboBox<Catalog> catalog;
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
        originalPrice = new LineEdit();
        discountValue = new LineEdit();
        discountType = new ComboBox(db.getDiscountType(), DiscountType.NAME_FIELD);
        tax = new ComboBox(db.getTax(), Tax.NAME_FIELD);
        unit = new ComboBox(db.getUnit(), Unit.NAME_FIELD);
        catalog = new ComboBox(db.getCatalog(), Catalog.NAME_FIELD);
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
        QGridLayout grid = new QGridLayout(widget);
        int nRow = 0;
        grid.addWidget(new QLabel(I18n.productCode), nRow, 0);
        grid.addWidget(code, nRow++, 1);
        grid.addWidget(new QLabel(I18n.catalog), nRow, 0);
        grid.addWidget(catalog, nRow++, 1);
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
        grid.addWidget(originalPrice, nRow++, 1);
        grid.addWidget(new QLabel(I18n.discountValue), nRow, 0);
        grid.addWidget(discountValue, nRow, 1);
        grid.addWidget(discountType, nRow++, 2);
//        grid.addWidget(new QLabel(I18n.tax), nRow, 0);
//        grid.addWidget(tax, nRow++, 1);
        grid.addWidget(new QLabel(I18n.finalPrice), nRow, 0);
        grid.addWidget(finalPrice, nRow++, 1);

        editor.addMapping(code, Product.SELF);
        editor.addMapping(catalog, Product.CATALOG_FIELD);
        editor.addMapping(supplier, Product.SUPPLIER_FIELD);
        editor.addMapping(size, Product.SIZE_FIELD);
        editor.addMapping(colour, Product.COLOUR_FIELD);
        editor.addMapping(packSize, Product.PACK_SIZE_FIELD, new IntegerToStringConverter());
        editor.addMapping(unit, Product.UNIT_FIELD);
        editor.addMapping(originalPrice, Product.ORIGINAL_PRICE_FIELD, new DoubleToStringConverter());
        editor.addMapping(discountValue, Product.DISCOUNT_VALUE_FIELD, new DoubleToStringConverter(0.0));
        editor.addMapping(discountType, Product.DISCOUNT_TYPE_FIELD);
        editor.addMapping(finalPrice, Product.FINAL_PRICE_FIELD, new CurrencyToStringConverter(0.0, Constants.PLN));

        originalPrice.textChanged.connect(this, "onPriceChanged()");
        discountValue.textChanged.connect(this, "onPriceChanged()");
        discountType.activatedIndex.connect(this, "onPriceChanged()");
        return widget;
    }

    private void onPriceChanged() {
        try {
            editor.commit(originalPrice);
            editor.commit(discountValue);
            editor.commit(discountType);
            bean.finalPrice = bean.getPriceWithDiscount();
            editor.flush(finalPrice);
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onOkBtnClicked() {
        try {
            super.onOkBtnClicked(); //To change body of generated methods, choose Tools | Templates.
            db.createOrUpdateProduct(bean);
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
            return;
        }
        close();
    }

    @Override
    protected boolean validate() {
        if (checkTextWidgetEmpty(discountValue, Styles.QLINEEDIT_RED_BORDER, true)) {
            discountValue.setText("0.0");
        }
        return isSuggestBoxStateInList(code, Styles.QLINEEDIT_RED_BORDER, false, bean)
                && checkTextWidgetEmpty(code, Styles.QLINEEDIT_RED_BORDER, false)
                && isSuggestBoxStateInList(supplier, Styles.QLINEEDIT_RED_BORDER, true)
                && isConvertable(packSize, Styles.QLINEEDIT_RED_BORDER)
                && isConvertable(originalPrice, Styles.QLINEEDIT_RED_BORDER)
                && isConvertable(discountValue, Styles.QLINEEDIT_RED_BORDER);
    }

    @Override
    protected void customFillBeanAfterCommit() {
        bean.code = code.text();
        if (bean.discountValue == null) {
            bean.discountValue = 0.0;
        }
        bean.company = Cache.getUserCompany();
    }
}
