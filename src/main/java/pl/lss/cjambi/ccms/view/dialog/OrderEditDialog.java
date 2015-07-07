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
import java.sql.SQLException;
import pl.lss.cjambi.ccms.bean.DiscountType;
import pl.lss.cjambi.ccms.bean.Item;
import pl.lss.cjambi.ccms.bean.Order;
import pl.lss.cjambi.ccms.bean.OrderStatus;
import pl.lss.cjambi.ccms.controller.exception.InvalidOrderItemException;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.utils.Constants;
import pl.lss.cjambi.ccms.utils.Styles;
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.utils.converter.CurrencyToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.DateTimeToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.DoubleToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.ForeignCollectionToListConverter;
import pl.lss.cjambi.ccms.utils.converter.IntegerToStringConverter;
import pl.lss.cjambi.ccms.view.HBoxWidget;
import pl.lss.cjambi.ccms.view.PageWidget;
import pl.lss.cjambi.ccms.view.table.OrderItemTable;
import pl.lss.cjambi.ccms.view.widget.ComboBox;
import pl.lss.cjambi.ccms.view.widget.Label;
import pl.lss.cjambi.ccms.view.widget.LineEdit;
import pl.lss.cjambi.ccms.view.widget.Refreshable;

/**
 *
 * @author ctran
 */
public class OrderEditDialog extends BeanEditDialog<Order> implements Refreshable {

    private static final int ORDER_EDIT_DIALOG_WIDTH = 800;

    private DbService db = DbServiceImpl.getInstance();
    private OrderItemTable itemsTable;
    private Label code, createdDate, value, total, packQuantity, productQuantity;
    private LineEdit discountValue;
    private ComboBox<DiscountType> discountType;
    private ComboBox<OrderStatus> status;
    private QPushButton newItemBtn;
//    private QPushButton printOrderBtn;

    public OrderEditDialog(Order order) {
        super();
        setBean(order);
        setMinimumWidth(ORDER_EDIT_DIALOG_WIDTH);
        code = new Label();
        createdDate = new Label();
        total = new Label();
        value = new Label();
        packQuantity = new Label();
        productQuantity = new Label();
        discountValue = new LineEdit();
        discountType = new ComboBox<>(db.getDiscountTypes(), DiscountType.NAME_FIELD);
        status = new ComboBox<>(Cache.getOrderStatuses(), OrderStatus.NAME_FIELD);

        itemsTable = new OrderItemTable(bean);
        itemsTable.setParentView(this);
        itemsTable.addColumn(I18n.supplier, Item.SUPPLIER_CODE_FIELD);
        itemsTable.addColumn(I18n.product, Item.PRODUCT_CODE_FIELD);
        itemsTable.addColumn(I18n.catalog, Item.PRODUCT_CATALOG_NAME_FIELD);
        itemsTable.addColumn(I18n.requiredPacks, Item.REQUIRED_PACK_FIELD, new IntegerToStringConverter());
        itemsTable.addColumn(I18n.packSize, Item.PRODUCT_PACK_SIZE_FIELD, new IntegerToStringConverter());
        itemsTable.addColumn(I18n.requiredProducts, Item.QUANTITY_FIELD, new IntegerToStringConverter());
        itemsTable.addColumn(I18n.finalPrice, Item.PRICE_FIELD, new DoubleToStringConverter());
        itemsTable.addColumn(I18n.value, Item.TOTAL_FIELD, new CurrencyToStringConverter(Constants.PLN));

        newItemBtn = new QPushButton(I18n.newItem);
        newItemBtn.clicked.connect(this, "onNewItemBtnClicked()");
//        printOrderBtn = new QPushButton(I18n.printOrder);
//        printOrderBtn.clicked.connect(this, "onPrintOrderBtnClicked()");

        discountValue.textChanged.connect(this, "onValueChanged()");
        discountType.activatedIndex.connect(this, "onValueChanged()");

        build();
        editor.setSomethingHasChanged(false);
//        printOrderBtn.setDisabled(editor.isSomethingHasChanged());
    }

//    private void onPrintOrderBtnClicked() {
//        bean.print();
//    }
    private void onValueChanged() {
        try {
            Double dv = (Double) editor.convertWidgetState(discountValue);
            DiscountType dt = (DiscountType) editor.convertWidgetState(discountType);
            Double t = (Double) editor.convertWidgetState(total);
            value.setState(Utils.round(Utils.getValueWithDiscount(t, dv, dt)));
        } catch (Exception ex) {
            reporter.error(I18n.discountIsNotValid);
        }
    }

    private void onNewItemBtnClicked() {
        ItemEditDialog dialog = new ItemEditDialog(bean);
        dialog.setBean(new Item());
        dialog.exec();
        try {
            itemsTable.addOrUpdateItem(dialog.getItem());
        } catch (InvalidOrderItemException ex) {
            reporter.error(I18n.invalidOrderItem);
        }
    }

    @Override
    protected QIcon getDialogIcon() {
        return IconResources.APP_ICON;
    }

    @Override
    protected String getDialogTitle() {
        return I18n.orderInfo;
    }

    @Override
    protected QWidget buildContent() {
        return new PageWidget() {

            @Override
            protected QWidget buildHeader() {
                HBoxWidget wrapper = new HBoxWidget();
                QWidget widget = new QWidget();
                QGridLayout grid = new QGridLayout(widget);
                int nRow = 0;
                grid.addWidget(new QLabel(I18n.orderNr), nRow, 0);
                grid.addWidget(code, nRow++, 1);
                grid.addWidget(new QLabel(I18n.createdDate), nRow, 0);
                grid.addWidget(createdDate, nRow++, 1);
                grid.addWidget(new QLabel(I18n.status), nRow, 0);
                grid.addWidget(status, nRow++, 1);
                grid.addWidget(newItemBtn, nRow++, 0);

                editor.addMapping(code, Order.CODE_FIELD);
                editor.addMapping(createdDate, Order.CREATED_DATE_FIELD, new DateTimeToStringConverter());
                editor.addMapping(status, Order.STATUS_FIELD);

                wrapper.addWidget(widget);
                wrapper.addSpacerItemToStretchWidget();
                return wrapper;
            }

            @Override
            protected QWidget buildContent() {
                editor.addMapping(itemsTable, Order.ITEMS_FIELD, new ForeignCollectionToListConverter(bean, Order.ITEMS_FIELD));
                return itemsTable;
            }

            @Override
            protected QWidget buildFooter() {
                HBoxWidget wrapper = new HBoxWidget();
                QWidget widget = new QWidget();
                QGridLayout layout = new QGridLayout(widget);
                int nRow = 0;
                layout.addWidget(new QLabel(I18n.requiredPacks), nRow, 0);
                layout.addWidget(packQuantity, nRow++, 1);
                layout.addWidget(new QLabel(I18n.requiredProducts), nRow, 0);
                layout.addWidget(productQuantity, nRow++, 1);
                layout.addWidget(new QLabel(I18n.total), nRow, 0);
                layout.addWidget(total, nRow++, 1);
                layout.addWidget(new QLabel(I18n.discountValue), nRow, 0);
                layout.addWidget(discountValue, nRow, 1);
                layout.addWidget(discountType, nRow++, 2);
                layout.addWidget(new QLabel(I18n.value), nRow, 0);
                layout.addWidget(value, nRow++, 1);
//                layout.addWidget(printOrderBtn, nRow, 0);

                editor.addMapping(packQuantity, Order.PACK_QUANTITY_FIELD, new IntegerToStringConverter(0));
                editor.addMapping(productQuantity, Order.PRODUCT_QUANTITY_FIELD, new IntegerToStringConverter(0));
                editor.addMapping(total, Order.TOTAL_FIELD, new CurrencyToStringConverter(0.0, Constants.PLN));
                editor.addMapping(discountValue, Order.DISCOUNT_VALUE_FIELD, new DoubleToStringConverter(0.0));
                editor.addMapping(discountType, Order.DISCOUNT_TYPE_FIELD);
                editor.addMapping(value, Order.VALUE_FIELD, new CurrencyToStringConverter(0.0, Constants.PLN));

                wrapper.addWidget(widget);
                wrapper.addSpacerItemToStretchWidget();
                return wrapper;
            }
        };
    }

    @Override
    protected void customFillBeanAfterCommit() throws SQLException {
        bean.company = Cache.getUserCompany();
    }

    @Override
    protected boolean validate() {
        return setStyleSheet(packQuantity, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(packQuantity) && isConvertable(packQuantity))
                && setStyleSheet(productQuantity, Styles.QLINEEDIT_RED_BORDER, !checkTextWidgetEmpty(productQuantity) && isConvertable(productQuantity))
                && setStyleSheet(discountValue, Styles.QLINEEDIT_RED_BORDER, checkTextWidgetEmpty(discountType) || isConvertable(discountValue));
    }

    @Override
    protected void onOkBtnClicked() throws Exception {
        try {
            super.onOkBtnClicked(); //To change body of generated methods, choose Tools | Templates.
            db.createOrUpdateOrder(bean);
            editor.setSomethingHasChanged(false);
//            printOrderBtn.setDisabled(editor.isSomethingHasChanged());
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
            return;
        }
        close();
    }

    @Override
    public void refresh() {
        Integer nPack = 0;
        Integer nProduct = 0;
        Double t = 0.0;
        for (Item item : itemsTable.getState()) {
            nPack += item.requiredPack;
            nProduct += item.quantity;
            t += item.value;
        }
        packQuantity.setState(nPack);
        productQuantity.setState(nProduct);
        total.setState(Utils.round(t));
        try {
            Double dv = (Double) editor.convertWidgetState(discountValue);
            DiscountType dt = (DiscountType) editor.convertWidgetState(discountType);
            value.setState(Utils.round(Utils.getValueWithDiscount(t, dv, dt)));
        } catch (Exception ex) {
        }
    }

    @Override
    protected String getOkBtnCaption() {
        return I18n.save;
    }
}
