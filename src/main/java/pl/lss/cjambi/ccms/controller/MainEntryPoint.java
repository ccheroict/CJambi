/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.controller;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QToolBar;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.utils.Constants;
import pl.lss.cjambi.ccms.utils.Styles;
import pl.lss.cjambi.ccms.view.dialog.LoginDialog;
import pl.lss.cjambi.ccms.view.dialog.ProductEditDialog;
import pl.lss.cjambi.ccms.view.dialog.SupplierEditDialog;
import pl.lss.cjambi.ccms.view.table.ProductTable;
import pl.lss.cjambi.ccms.view.table.SupplierTable;

/**
 *
 * @author ctran
 */
public class MainEntryPoint extends QMainWindow {

    private static final String appTitle = Constants.APP_NAME + " " + Constants.APP_VERSION;
    private static final DbService db = DbServiceImpl.getInstance();

    public MainEntryPoint() {
        setWindowTitle(appTitle);
        setWindowIcon(IconResources.APP_ICON);
        setMinimumHeight(600);
        setMinimumWidth(800);
        buildMenuBar();
        buildToolBar();
    }

    public static void main(String[] args) {
        QApplication.initialize(args);

        MainEntryPoint app = new MainEntryPoint();
        app.guaranteeDatabaseIsInitialized();
        if (app.authorizeUser()) {
            app.show();
        } else {
            QTimer.singleShot(0, app, "close()");
        }

        QApplication.execStatic();
    }

    private void guaranteeDatabaseIsInitialized() {
        db.createTablesIfNessecary();
    }

    private boolean authorizeUser() {
        LoginDialog dialog = LoginDialog.getInstance();
        return (dialog.exec() != 0);
    }

    private void buildMenuBar() {
        menuBar().addMenu(buildWarehouseMenu());
        menuBar().addMenu(buildOrderMenu());
    }

    private QMenu buildWarehouseMenu() {
        QMenu menu = new QMenu();
        menu.setTitle(I18n.warehouse);
//        menu.addAction(Singletons.getAddSupplierAction());

        QAction getSupplierListAction = new QAction(I18n.supplierList, null);
        getSupplierListAction.triggered.connect(this, "onSupplierTableActived()");
        menu.addAction(getSupplierListAction);

        QAction getProductListAction = new QAction(I18n.productList, null);
        getProductListAction.triggered.connect(this, "onProductTableActivated()");
        menu.addAction(getProductListAction);

        return menu;
    }

    private void onSupplierTableActived() {
        setWindowTitle(appTitle + " - " + I18n.supplierList);
        SupplierTable table = new SupplierTable();
        table.refresh();
        setCentralWidget(table);
    }

    private void onProductTableActivated() {
        setWindowTitle(appTitle + " - " + I18n.productList);
        ProductTable table = new ProductTable();
        table.refresh();
        setCentralWidget(table);
    }

    private QMenu buildOrderMenu() {
        QMenu menu = new QMenu();
        menu.setTitle(I18n.order);

        QAction getOrderListAction = new QAction(I18n.orderList, null);
        getOrderListAction.triggered.connect(this, "onOrderTableActived()");
        menu.addAction(getOrderListAction);

        return menu;
    }

    private void onOrderTableActived() {
        setWindowTitle(appTitle + " - " + I18n.orderList);
//        OrderGrid grid = new OrderGrid();
//        grid.build();
//        setCentralWidget(grid);
//        grid.refresh();
    }

    private void buildToolBar() {
        QToolBar toolbar = addToolBar("toolbar");
        toolbar.setStyleSheet(Styles.TOOLBAR);

        QAction addSupplierAction = new QAction(IconResources.SUPPIER_ICON, I18n.addNewSupplier, null);
        addSupplierAction.triggered.connect(this, "onAddSupplierAction()");
        toolbar.addAction(addSupplierAction);

        QAction addProductAction = new QAction(IconResources.PRODUCT_ICON, I18n.addNewProduct, null);
        addProductAction.triggered.connect(this, "onAddProductAction()");
        toolbar.addAction(addProductAction);

        QAction addOrderAction = new QAction(IconResources.ORDER_ICON, I18n.addNewOrder, null);
        addOrderAction.triggered.connect(this, "onAddOrderAction()");
        toolbar.addAction(addOrderAction);
    }

    private void onAddSupplierAction() {
//        SupplierEditDialog dialog = SupplierEditDialog.getInstance();
        SupplierEditDialog dialog = new SupplierEditDialog();
        dialog.setBean(new Supplier());
        dialog.exec();
    }

    private void onAddProductAction() {
//        ProductEditDialog dialog = ProductEditDialog.getInstance();
        ProductEditDialog dialog = new ProductEditDialog();
        dialog.setBean(new Product());
        dialog.exec();
    }

    private void onAddOrderAction() {
//        OrderEditDialog dialog = new OrderEditDialog(null);
//        dialog.exec();
//        refreshCentralWidget();
    }

}
