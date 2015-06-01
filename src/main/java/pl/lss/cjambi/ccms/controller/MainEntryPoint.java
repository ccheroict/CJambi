/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.view.InformationDialog;
import pl.lss.cjambi.ccms.view.LoginDialog;

/**
 *
 * @author ctran
 */
@Singleton
public class MainEntryPoint extends QMainWindow {

    @Inject
    private DbService db;

    public static void main(String[] args) {
        QApplication.initialize(args);

        MainEntryPoint app = Cache.getInstance(MainEntryPoint.class);
        app.guaranteeDatabaseIsInitialized();
        if (app.authorizeUser()) {
            System.out.println(Cache.getInstance(InformationDialog.class)
                    .setType(InformationDialog.InformationType.INFO)
                    .setMessage("HAHAHA")
                    .build()
                    .exec());
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
}
