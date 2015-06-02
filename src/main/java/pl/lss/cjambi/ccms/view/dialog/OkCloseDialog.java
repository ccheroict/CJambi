/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QDialogButtonBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;

/**
 *
 * @author ctran
 */
public abstract class OkCloseDialog extends OkDialog {

    protected QPushButton closeBtn;

    protected OkCloseDialog() {
        super();
        closeBtn = new QPushButton(IconResources.CANCEL_ICON, I18n.close);
    }

    @Override
    protected QWidget buildButtons() {
        QDialogButtonBox box = new QDialogButtonBox();
        box.addButton(okBtn, QDialogButtonBox.ButtonRole.AcceptRole);
        box.addButton(closeBtn, QDialogButtonBox.ButtonRole.RejectRole);
        okBtn.clicked.connect(this, "onOkBtnClicked()");
        closeBtn.clicked.connect(this, "onCloseBtnClicked()");
        return box;
    }

    protected void onCloseBtnClicked() {
        close();
        setResult(DialogCode.Rejected.value());
    }
}
