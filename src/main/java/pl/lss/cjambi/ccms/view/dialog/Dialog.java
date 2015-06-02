/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 *
 * @author ctran
 */
public abstract class Dialog extends QDialog {

    protected QVBoxLayout layout;

    protected Dialog() {
        super();
        layout = new QVBoxLayout();
    }

    public Dialog build() {
        setWindowIcon(getDialogIcon());
        setWindowTitle(getDialogTitle());

        layout.addWidget(buildContent());
        layout.addWidget(buildButtons());
        setLayout(layout);

        return this;
    }

    protected abstract QIcon getDialogIcon();

    protected abstract String getDialogTitle();

    protected abstract QWidget buildContent();

    protected abstract QWidget buildButtons();
}
