/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
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
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.utils.converter.CurrencyToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.DoubleToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.IntegerToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.SupplierToCodeConverter;
import pl.lss.cjambi.ccms.view.widget.ComboBox;
import pl.lss.cjambi.ccms.view.widget.Label;
import pl.lss.cjambi.ccms.view.widget.LineEdit;
import pl.lss.cjambi.ccms.view.widget.PlainTextEdit;
import pl.lss.cjambi.ccms.view.widget.PrintUtils;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class ProductEditDialog extends BeanEditDialog<Product> {

//    private SuggestBox<Product> code;
    private LineEdit code;
    private SuggestBox<Supplier> supplier;
    private LineEdit size, colour, packSize, originalPrice, discountValue;
    private ComboBox<Catalog> catalog;
    private ComboBox<DiscountType> discountType;
    private ComboBox<Tax> tax;
    private ComboBox<Unit> unit;
    private Label finalPrice;
    private PlainTextEdit note;
    private QPushButton printBtn;

    public ProductEditDialog(Product product) {
        super();
        setBean(product);
//        code = new SuggestBox(Product.class, Product.CODE_FIELD) {
//
//            @Override
//            public List fetchData() {
//                Filter filter = new Filter();
//                filter.productCode = text();
//                Object obj;
//                try {
//                    obj = editor.convertWidgetState(supplier);
//                    if (obj != null) {
//                        filter.supplier = (Supplier) obj;
//                    }
//                } catch (Exception ex) {
//                }
//                return db.getProduct(filter);
//            }
//        };
        code = new LineEdit();
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
        discountType = new ComboBox(db.getDiscountTypes(), DiscountType.NAME_FIELD);
        tax = new ComboBox(db.getTax(), Tax.NAME_FIELD);
        unit = new ComboBox(db.getUnit(), Unit.NAME_FIELD);
        catalog = new ComboBox(db.getCatalog(), Catalog.NAME_FIELD);
        finalPrice = new Label();
        note = new PlainTextEdit();
        printBtn = new QPushButton(I18n.print);
        printBtn.setEnabled(bean.id != null);
        printBtn.clicked.connect(this, "onPrintBtnClicked()");

        build();
    }

    private void onPrintBtnClicked() {
        PrintUtils.print(bean);
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
        grid.addWidget(new QLabel(I18n.supplierCode), nRow, 0);
        grid.addWidget(supplier, nRow++, 1);
        grid.addWidget(new QLabel(I18n.productCode), nRow, 0);
        grid.addWidget(code, nRow++, 1);
        grid.addWidget(new QLabel(I18n.catalog), nRow, 0);
        grid.addWidget(catalog, nRow++, 1);
        grid.addWidget(new QLabel(I18n.size), nRow, 0);
        grid.addWidget(size, nRow++, 1);
        grid.addWidget(new QLabel(I18n.colour), nRow, 0);
        grid.addWidget(colour, nRow++, 1);
        grid.addWidget(new QLabel(I18n.packSize), nRow, 0);
        grid.addWidget(packSize, nRow, 1);
        grid.addWidget(unit, nRow++, 2);
        grid.addWidget(new QLabel(I18n.originalPrice), nRow, 0);
        grid.addWidget(originalPrice, nRow++, 1);
        grid.addWidget(new QLabel(I18n.discountValue), nRow, 0);
        grid.addWidget(discountValue, nRow, 1);
        grid.addWidget(discountType, nRow++, 2);
//        grid.addWidget(new QLabel(I18n.tax), nRow, 0);
//        grid.addWidget(tax, nRow++, 1);
        grid.addWidget(new QLabel(I18n.finalPrice), nRow, 0);
        grid.addWidget(finalPrice, nRow++, 1);
        grid.addWidget(new QLabel(I18n.note), nRow, 0);
        grid.addWidget(note, nRow++, 1);
        grid.addWidget(printBtn, nRow, 0);

        editor.addMapping(supplier, Product.SUPPLIER_FIELD, new SupplierToCodeConverter());
        editor.addMapping(code, Product.CODE_FIELD);
        editor.addMapping(catalog, Product.CATALOG_FIELD);
        editor.addMapping(size, Product.SIZE_FIELD);
        editor.addMapping(colour, Product.COLOUR_FIELD);
        editor.addMapping(packSize, Product.PACK_SIZE_FIELD, new IntegerToStringConverter(0));
        editor.addMapping(unit, Product.UNIT_FIELD);
        editor.addMapping(originalPrice, Product.ORIGINAL_PRICE_FIELD, new DoubleToStringConverter(0.0));
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
            Double op = (Double) editor.convertWidgetState(originalPrice);
            Double dv = (Double) editor.convertWidgetState(discountValue);
            DiscountType dt = (DiscountType) editor.convertWidgetState(discountType);
            editor.setWidgetState(finalPrice, Utils.getValueWithDiscount(op, dv, dt));
        } catch (Exception ex) {
        }
    }

    @Override
    protected void onOkBtnClicked() {
        try {
            super.onOkBtnClicked(); //To change body of generated methods, choose Tools | Templates.
            db.createOrUpdateProduct(bean);
            printBtn.setEnabled(bean.id != null);
//            close();
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
            return;
        }
    }

    @Override
    protected boolean validate() {
//        return setStyleSheet(code, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(code) && stateIsNullOrItself(code, bean))
//                && setStyleSheet(supplier, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(supplier) && isConvertable(supplier))
//                && setStyleSheet(packSize, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(packSize) && isConvertable(packSize) && (Integer) tryConvert(packSize) > 0)
//                && setStyleSheet(originalPrice, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(originalPrice) && isConvertable(originalPrice))
//                && setStyleSheet(discountValue, Styles.QLINEEDIT_RED_BORDER, checkTextWidgetEmpty(discountValue) || isConvertable(discountValue));

        return setStyleSheet(code, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(code) /*&& stateIsNullOrItself(code, bean)*/)
                && setStyleSheet(supplier, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(supplier) && isConvertable(supplier))
                && setStyleSheet(packSize, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(packSize) && isConvertable(packSize) && (Integer) tryConvert(packSize) > 0)
                && setStyleSheet(originalPrice, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(originalPrice) && isConvertable(originalPrice))
                && setStyleSheet(discountValue, Styles.QLINEEDIT_RED_BORDER, checkTextWidgetEmpty(discountValue) || isConvertable(discountValue));
    }

    @Override
    protected void customFillBeanAfterCommit() {
        bean.code = code.text();
        bean.company = Cache.getUserCompany();
    }
}
