/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.gui.QDialogButtonBox;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.resources.I18n;

/**
 *
 * @author ctran
 */
public abstract class AcceptDialog extends Dialog {

    private QPushButton acceptBtn;

    public AcceptDialog() {
        acceptBtn = new QPushButton(I18n.OK);
        acceptBtn.clicked.connect(this, "onAcceptBtnClicked()");
    }

    @Override
    public QWidget buildButtons() {
        QDialogButtonBox buttonBox = new QDialogButtonBox();
        buttonBox.addButton(acceptBtn, QDialogButtonBox.ButtonRole.AcceptRole);
        return buttonBox;
    }

    public abstract void onAcceptBtnClicked();
}
