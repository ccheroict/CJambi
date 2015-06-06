/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPlainTextEdit;
import com.trolltech.qt.gui.QWidget;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.DialogErrorReporter;
import pl.lss.cjambi.ccms.utils.Editor;
import pl.lss.cjambi.ccms.utils.ErrorReporter;
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.view.widget.HasState;

/**
 *
 * @author ctran
 */
public abstract class BeanEditDialog<T> extends OkCloseDialog {

    private static final Logger logger = Logger.getLogger(BeanEditDialog.class);
    protected static final ErrorReporter reporter = DialogErrorReporter.getInstance();
    protected static final DbService db = DbServiceImpl.getInstance();

    protected Editor<T> editor;
    protected T bean;

    protected BeanEditDialog() {
        super();
        editor = new Editor();
    }

    public void setBean(T bean) {
        this.bean = bean;
        editor.setBean(bean);
    }

    @Override
    public int exec() {
        try {
            editor.flush();
            customFillWidgetsBeforeExec();
            return super.exec();
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
            return 0;
        }
    }

    @Override
    protected void onOkBtnClicked() throws Exception {
        if (!validate()) {
            throw new Exception();
        }
        editor.commit();
        customFillBeanAfterCommit();
    }

    protected boolean validate() {
        return true;
    }

    protected boolean stateIsNullOrItself(HasState widget, Object itSelf) {
        try {
            Object obj = tryConvert(widget);
            return (obj == null || BeanUtils.getProperty(itSelf, "id") == BeanUtils.getProperty(obj, "id"));
        } catch (Exception ex) {
            return false;
        }
    }

    protected boolean checkTextWidgetEmpty(HasState widget) {
        String text = null;
        if (widget instanceof QLineEdit) {
            text = ((QLineEdit) widget).text();
        } else if (widget instanceof QPlainTextEdit) {
            text = ((QPlainTextEdit) widget).toPlainText();
        } else {
            text = Utils.toStringOrEmpty(widget.getState());
        }
        boolean res;
        res = (text == null || text.isEmpty());
        return res;
    }

    protected boolean isConvertable(HasState widget) {
        return (tryConvert(widget) != null);
    }

    protected Object tryConvert(HasState widget) {
        try {
            return editor.convertWidgetState(widget);
        } catch (Exception ex) {
            return null;
        }
    }

    protected boolean setStyleSheet(QWidget widget, String invalidStyle, boolean res) {
        if (res) {
            widget.setStyleSheet("");
        } else {
            widget.setStyleSheet(invalidStyle);
        }
        return res;
    }

    protected void customFillWidgetsBeforeExec() throws Exception {
        //NO-OP
    }

    protected void customFillBeanAfterCommit() throws Exception {
        //NO-OPP
    }
}
