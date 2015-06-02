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
public abstract class OkDialog extends Dialog {

    protected QPushButton okBtn;

    public OkDialog() {
        super();
        okBtn = new QPushButton(IconResources.ACTION_ICON, I18n.OK);
    }

    @Override
    protected QWidget buildButtons() {
        QDialogButtonBox box = new QDialogButtonBox();
        box.addButton(okBtn, QDialogButtonBox.ButtonRole.AcceptRole);
        okBtn.clicked.connect(this, "onOkBtnClicked()");
        return box;
    }

    protected void onOkBtnClicked() throws Exception {
        close();
        setResult(DialogCode.Accepted.value());
    }
}
