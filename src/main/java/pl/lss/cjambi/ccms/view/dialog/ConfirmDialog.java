/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.google.inject.Singleton;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;

/**
 *
 * @author ctran
 */
@Singleton
public class ConfirmDialog extends OkCloseDialog {

    private QLabel message;

    public ConfirmDialog() {
        super();
        message = new QLabel();
    }

    @Override
    protected QIcon getDialogIcon() {
        return IconResources.WARNING_ICON;
    }

    @Override
    protected String getDialogTitle() {
        return I18n.warning;
    }

    @Override
    protected QWidget buildContent() {
        return message;
    }

    public ConfirmDialog setMessage(String message) {
        this.message.setText(message);
        return this;
    }
}
