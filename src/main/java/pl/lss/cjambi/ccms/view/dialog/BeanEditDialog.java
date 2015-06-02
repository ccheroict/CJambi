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
import pl.lss.cjambi.ccms.controller.exception.InvalidInformationException;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.DialogErrorReporter;
import pl.lss.cjambi.ccms.utils.Editor;
import pl.lss.cjambi.ccms.utils.ErrorReporter;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

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
            customFillBeforeExec();
            return super.exec();
        } catch (Exception ex) {
            logger.error("exec", ex);
            reporter.error(I18n.sorryErrorHasAppeared);
            return 0;
        }
    }

    @Override
    protected void onOkBtnClicked() throws Exception {
        if (!validate()) {
            throw new InvalidInformationException();
        }
        editor.commit();
    }

    protected boolean validate() {
        return true;
    }

    protected boolean validateSuggestBoxStateInList(SuggestBox box, String invalidStyle, boolean desire, Object... exceptions) {
        for (int i = 0; i < exceptions.length; i++) {
            if (box.getState() == exceptions[i]) {
                setStyleSheet(box, invalidStyle, true);
                return true;
            }
        }
        boolean res = (box.isValidState() == desire);
        setStyleSheet(box, invalidStyle, box.isValidState() == desire);
        return res;
    }

    protected boolean validateTextWidgetIsEmpty(QWidget widget, String invalidStyle, boolean desire, Object... exceptions) {
        String text = null;
        if (widget instanceof QLineEdit) {
            text = ((QLineEdit) widget).text();
        } else if (widget instanceof QPlainTextEdit) {
            text = ((QPlainTextEdit) widget).toPlainText();
        }
        boolean res;
        if (!desire) {
            res = (text != null && !text.isEmpty());
            setStyleSheet(widget, invalidStyle, res);
            return res;
        }
        res = (text == null || text.isEmpty());
        setStyleSheet(widget, invalidStyle, res);
        return res;
    }

    private void setStyleSheet(QWidget widget, String invalidStyle, boolean res) {
        if (res) {
            widget.setStyleSheet("");
        } else {
            widget.setStyleSheet(invalidStyle);
        }
    }

    protected abstract void customFillBeforeExec();
}
