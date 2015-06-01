/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QToolBar;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.utils.Constants;
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.view.LoginDialog;
import pl.lss.jambi.ccms.resources.Styles;

/**
 *
 * @author ctran
 */
@Singleton
public class MainEntryPoint extends QMainWindow {

    private String appTitle = Constants.APP_NAME + " " + Constants.APP_VERSION;

    @Inject
    private static DbService db;

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

        MainEntryPoint app = Cache.getInstance(MainEntryPoint.class);
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
        LoginDialog dialog = Cache.getInstance(LoginDialog.class);
        return (dialog.build().exec() != 0);
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
        getSupplierListAction.triggered.connect(this, "onSupplierListGridActived()");
        menu.addAction(getSupplierListAction);

        QAction getProductListAction = new QAction(I18n.productList, null);
        getProductListAction.triggered.connect(this, "onProductListGridActivated()");
        menu.addAction(getProductListAction);

        return menu;
    }

    private void onSupplierListGridActived() {
        setWindowTitle(appTitle + " - " + I18n.supplierList);
//        SupplierGrid grid = new SupplierGrid();
//        grid.build();
//        setCentralWidget(grid);
//        grid.refresh();
    }

    private void onProductListGridActivated() {
        setWindowTitle(appTitle + " - " + I18n.productList);
//        ProductGrid grid = new ProductGrid();
//        grid.build();
//        setCentralWidget(grid);
//        grid.refresh();
    }

    private void onClientListGridActived() {
        setWindowTitle(appTitle + " - " + I18n.clientList);
//        ClientGrid grid = new ClientGrid();
//        grid.build();
//        setCentralWidget(grid);
//        grid.refresh();
    }

    private void onOrderListGridActived() {
        setWindowTitle(appTitle + " - " + I18n.orderList);
//        OrderGrid grid = new OrderGrid();
//        grid.build();
//        setCentralWidget(grid);
//        grid.refresh();
    }

    private void buildToolBar() {
        QToolBar toolbar = addToolBar("toolbar");
        toolbar.setStyleSheet(Styles.toolbar);
        toolbar.addAction(Utils.createAddSupplierAction());
        toolbar.addAction(Utils.createAddProductAction());
        toolbar.addAction(Utils.createAddOrderAction());
        toolbar.addAction(Utils.createAddClientAction());
    }

    private QMenu buildOrderMenu() {
        QMenu menu = new QMenu();
        menu.setTitle(I18n.order);

        QAction getClientListAction = new QAction(I18n.clientList, null);
        getClientListAction.triggered.connect(this, "onClientListGridActived()");
        menu.addAction(getClientListAction);

        QAction getOrderListAction = new QAction(I18n.orderList, null);
        getOrderListAction.triggered.connect(this, "onOrderListGridActived()");
        menu.addAction(getOrderListAction);

        return menu;
    }
}
