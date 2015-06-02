/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.google.inject.Inject;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPlainTextEdit;
import com.trolltech.qt.gui.QWidget;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.controller.exception.InvalidInformationException;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.Editor;
import pl.lss.cjambi.ccms.utils.ErrorReporter;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public abstract class BeanEditDialog<T> extends OkCloseDialog {

    private static final Logger logger = Logger.getLogger(BeanEditDialog.class);
    @Inject
    protected ErrorReporter reporter;
    @Inject
    protected DbService db;

    private Editor<T> editor;
    protected T bean;

    public BeanEditDialog() {
        super();
        editor = Cache.getInstance(Editor.class);
    }

    public void setBean(T bean) {
        this.bean = bean;
        editor.setBean(bean);
    }

    protected void addMapping(QWidget widget, String propName) {
        editor.addMapping(widget, propName);
    }

    @Override
    public int exec() {
        try {
            editor.flush();
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

    protected boolean validateSuggestBoxStateInList(SuggestBox box, boolean desire, String invalidStyle) {
        if (box.isValidState() == desire) {
            box.setStyleSheet("");
            return true;
        } else {
            box.setStyleSheet(invalidStyle);
            return false;
        }
    }

    protected boolean validateTextWidgetIsEmpty(QWidget widget, boolean desire, String invalidStyle) {
        String text = null;
        if (widget instanceof QLineEdit) {
            text = ((QLineEdit) widget).text();
        } else if (widget instanceof QPlainTextEdit) {
            text = ((QPlainTextEdit) widget).toPlainText();
        }
        if (desire) {
            return (text != null && !text.isEmpty());
        }
        return (text == null || text.isEmpty());
    }
}
