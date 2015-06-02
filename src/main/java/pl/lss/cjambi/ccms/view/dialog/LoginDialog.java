/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QFormLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.bean.User;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;

/**
 *
 * @author ctran
 */
public class LoginDialog extends OkDialog {

    private static final LoginDialog instance = new LoginDialog();
    private static final DbService db = DbServiceImpl.getInstance();

    public static LoginDialog getInstance() {
        return instance;
    }

    private QLineEdit username, password;

    private LoginDialog() {
        super();
        username = new QLineEdit();
        password = new QLineEdit();
        password.setEchoMode(QLineEdit.EchoMode.Password);
        build();
    }

    @Override
    protected void onOkBtnClicked() {
        User user = db.login(username.text(), password.text());
        if (user != null) {
            Cache.setUser(user);
            close();
        } else {
            username.setText("");
            password.setText("");
        }
        setResult(DialogCode.Accepted.value());
    }

    @Override
    protected QIcon getDialogIcon() {
        return IconResources.APP_ICON;
    }

    @Override
    protected String getDialogTitle() {
        return I18n.login;
    }

    @Override
    protected QWidget buildContent() {
        QWidget w = new QWidget();
        QFormLayout layout = new QFormLayout(w);
        layout.addRow(new QLabel(I18n.username), username);
        layout.addRow(new QLabel(I18n.password), password);
        return w;
    }
}
