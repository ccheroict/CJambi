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
import pl.lss.cjambi.ccms.bean.Item;
import pl.lss.cjambi.ccms.bean.Order;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.Styles;
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.utils.converter.DoubleToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.IntegerToStringConverter;
import pl.lss.cjambi.ccms.view.widget.LineEdit;

/**
 *
 * @author ctran
 */
public class ItemEditDialog extends BeanEditDialog<Item> {

    private DbService db = DbServiceImpl.getInstance();
    private Order order;
//    private SuggestBox product;
    private LineEdit id;
    private QLabel supplier, packSizeLbl;
    private LineEdit requiredPack, price, quantity, value;
    private Integer packSize;

    private Supplier selectedSupplier = null;
    private Product selectedProduct = null;

    public ItemEditDialog(Order order) {
        super();
//        product = new SuggestBox(Product.class, Product.CODE_FIELD) {
//
//            @Override
//            public List fetchData() {
//                Filter filter = new Filter();
//                filter.productCode = text();
//                return db.getProduct(filter);
//            }
//        };
        id = new LineEdit();
//        product.completer().activatedIndex.connect(this, "onProductChanged()");
        id.editingFinished.connect(this, "onProductChanged()");
        supplier = new QLabel();
        packSizeLbl = new QLabel();
        requiredPack = new LineEdit();
        quantity = new LineEdit();
        price = new LineEdit();
        value = new LineEdit();

        requiredPack.textChanged.connect(this, "onRequiredPackChanged()");
        quantity.textChanged.connect(this, "onQuantityOrPriceChanged()");
        price.textChanged.connect(this, "onQuantityOrPriceChanged()");

        build();
    }

    private void onRequiredPackChanged() {
        try {
            Integer rp = (Integer) editor.convertWidgetState(requiredPack);
            quantity.setState(rp * packSize);
        } catch (Exception ex) {
        }
    }

    private void onQuantityOrPriceChanged() {
        try {
            Integer q = (Integer) editor.convertWidgetState(quantity);
            Double p = (Double) editor.convertWidgetState(price);
            value.setState(Utils.round(q * p));
        } catch (Exception ex) {
        }
    }

    private void onProductChanged() {
        try {
            selectedProduct = null;
            selectedSupplier = null;
//            Object obj = tryConvert(product);
//            if (obj == null) {
//                return;
//            }
//            Product selectedProduct = (Product) obj;
            if (id.text() == null || id.text().isEmpty()) {
                return;
            }
            selectedProduct = db.getProductById(Integer.parseInt(id.text()));
            selectedSupplier = selectedProduct.supplier;
            updateProductInfo(selectedProduct);
            requiredPack.setState(0);
            quantity.setState(0);
            price.setState(BeanUtils.getProperty(selectedProduct, Product.FINAL_PRICE_FIELD));
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
        }
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
        QFormLayout form = new QFormLayout(widget);
//        form.addRow(new QLabel(I18n.product), product);
        form.addRow(new QLabel(I18n.ID), id);
        form.addRow(new QLabel(I18n.supplier), supplier);
        form.addRow(new QLabel(I18n.packSize), packSizeLbl);
        form.addRow(new QLabel(I18n.requiredPacks), requiredPack);
        form.addRow(new QLabel(I18n.quantity), quantity);
        form.addRow(new QLabel(I18n.finalPrice), price);
        form.addRow(new QLabel(I18n.value), value);

//        editor.addMapping(product, Item.PRODUCT_FIELD, new ProductToCodeConverter() {
//
//            @Override
//            public Supplier getSupplier() {
//                return selectedSupplier;
//            }
//        });
        editor.addMapping(requiredPack, Item.REQUIRED_PACK_FIELD, new IntegerToStringConverter(0));
        editor.addMapping(quantity, Item.QUANTITY_FIELD, new IntegerToStringConverter());
        editor.addMapping(price, Item.PRICE_FIELD, new DoubleToStringConverter());
        editor.addMapping(value, Item.TOTAL_FIELD, new DoubleToStringConverter());

        return widget;
    }

    public Item getItem() {
        return bean;
    }

    @Override
    protected void onOkBtnClicked() throws Exception {
        super.onOkBtnClicked(); //To change body of generated methods, choose Tools | Templates.
        close();
    }

    @Override
    protected void customFillBeanAfterCommit() {
        bean.order = order;
        bean.product = selectedProduct;
    }

    @Override
    protected boolean validate() {
        return setStyleSheet(id, Styles.QLINEEDIT_RED_BORDER, selectedProduct != null)
                && setStyleSheet(requiredPack, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(requiredPack) && isConvertable(requiredPack))
                && setStyleSheet(quantity, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(quantity) && isConvertable(quantity))
                && setStyleSheet(price, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(price) && isConvertable(price) && ((Double) tryConvert(price) > 0))
                && setStyleSheet(value, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(value) && isConvertable(value));
    }

    public void updateProductInfo(Product selectedProduct) {
        try {
            supplier.setText((String) BeanUtils.getProperty(selectedProduct, Product.SUPPLIER_CODE_FIELD));
            packSize = (Integer) BeanUtils.getProperty(selectedProduct, Product.PACK_SIZE_FIELD);
            packSizeLbl.setText(Utils.toStringOrEmpty(packSize));
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
        }
    }

    @Override
    protected void customFillWidgetsBeforeExec() throws Exception {
        if (bean.product != null) {
            id.setState(bean.product.id);
            selectedProduct = db.getProductById(Integer.parseInt(id.text()));
            id.setEnabled(false);
        }
    }
}
