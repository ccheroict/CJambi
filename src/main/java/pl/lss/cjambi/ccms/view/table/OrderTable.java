/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.table;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import java.util.List;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.ccms.cjambi.bean.Order;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.Constants;
import pl.lss.cjambi.ccms.utils.PrintUtils;
import pl.lss.cjambi.ccms.utils.converter.CurrencyToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.DateTimeToStringConverter;
import pl.lss.cjambi.ccms.view.HBoxWidget;
import pl.lss.cjambi.ccms.view.PageWidget;
import pl.lss.cjambi.ccms.view.VBoxWidget;
import pl.lss.cjambi.ccms.view.dialog.OrderEditDialog;
import pl.lss.cjambi.ccms.view.dialog.OrderSearchEditDialog;
import pl.lss.cjambi.ccms.view.widget.Table;

/**
 *
 * @author ctran
 */
public class OrderTable extends PageWidget {

    private static final DbService db = DbServiceImpl.getInstance();

    private Table<Order> table;
    private Filter filter = new Filter();
    private Pager pager;
    private QLabel total;

    public OrderTable() {
        super();
    }

    @Override
    protected QWidget buildHeader() {
        HBoxWidget header = new HBoxWidget();
        header.addWidget(new QLabel(I18n.orderList));
        QPushButton showFilterBtn = new QPushButton(I18n.search);
        showFilterBtn.clicked.connect(this, "onShowFilterBtnClicked()");
        header.addWidget(showFilterBtn);
        header.addSpacerItemToStretchWidget();
        return header;
    }

    private void onShowFilterBtnClicked() {
        OrderSearchEditDialog dialog = new OrderSearchEditDialog();
        dialog.setBean(filter);
        dialog.exec();
        pager.onRefreshBtnCLicked();
    }

    @Override
    protected QWidget buildContent() {
        table = new Table<Order>() {

            @Override
            protected void showEditDialog(Order selected) {
//                SupplierEditDialog dialog = SupplierEditDialog.getInstance();
                OrderEditDialog dialog = new OrderEditDialog(selected);
                dialog.exec();
                pager.onRefreshBtnCLicked();
            }

            @Override
            protected void onDeleteActionSelected() {
                List<QModelIndex> selectedRows = selectionModel().selectedRows();
                for (QModelIndex index : selectedRows) {
                    Order order = state.get(index.row());
                    order.isActive = 0;
                    db.createOrUpdateOrder(order);
                }
                pager.onRefreshBtnCLicked();
                selectionModel().clearSelection();
            }

            @Override
            protected void onPrintActionSelected() {
                Order order = state.get(selectionModel().selectedRows().get(0).row());
                PrintUtils.print(order);
            }
        };
        table.addColumn(I18n.orderNr, Order.CODE_FIELD);
        table.addColumn(I18n.status, Order.STATUS_NAME_FIELD);
        table.addColumn(I18n.requiredPacks, Order.PACK_QUANTITY_FIELD);
        table.addColumn(I18n.requiredProducts, Order.PRODUCT_QUANTITY_FIELD);
        table.addColumn(I18n.discountValue, Order.DISCOUNT_VALUE_FIELD);
        table.addColumn(I18n.discountType, Order.DISCOUNT_TYPE_NAME_FIELD);
        table.addColumn(I18n.value, Order.VALUE_FIELD, new CurrencyToStringConverter(Constants.PLN));
        table.addColumn(I18n.createdDate, Order.CREATED_DATE_FIELD, new DateTimeToStringConverter());
        return table;
    }

    @Override
    protected QWidget buildFooter() {
        VBoxWidget footer = new VBoxWidget();
        footer.addWidget(total = new QLabel(I18n.total + ": "));
        pager = new Pager(Table.DEFAULT_SIZE) {

            @Override
            protected long getMaxPage() {
                long cnt = db.countOrder(filter);
                total.setText(I18n.total + ": " + cnt);
                return ((cnt - 1) / Table.DEFAULT_SIZE + 1);
            }

            @Override
            protected void fetchDataAndRefreshTable() {
                filter.pageSize = pageSize;
                filter.pageNum = pageNum - 1;
                table.setState(db.getOrder(filter));
            }
        };
        footer.addWidget(pager);
        return footer;
    }

    public void refresh() {
        pager.onRefreshBtnCLicked();
    }
}
