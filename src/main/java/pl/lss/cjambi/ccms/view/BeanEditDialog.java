/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.google.inject.Inject;
import java.text.ParseException;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.Editor;
import pl.lss.cjambi.ccms.utils.ErrorReporter;

/**
 *
 * @author ctran
 */
public abstract class BeanEditDialog<T> extends OkCloseDialog {

    private static final Logger logger = Logger.getLogger(BeanEditDialog.class);
    @Inject
    private static ErrorReporter reporter;

    private T bean;
    private Editor<T> editor;

    public BeanEditDialog(T bean) {
        super();
        if (bean == null) {
            bean = createBean();
        }
        this.bean = bean;
        editor = new Editor(bean);
    }

    @Override
    public int exec() {
        try {
            editor.flush();
            return super.exec();
        } catch (Exception ex) {
            logger.error("exec", ex);
            reporter.error(I18n.sorryErrorAppeared);
            return 0;
        }
    }

    @Override
    public void onOkBtnClicked() {
        if (!validate()) {
            return;
        }
        try {
            editor.commit();
        } catch (ParseException ex) {
            logger.error("onOkBtnClicked", ex);
            reporter.error(I18n.sorryErrorAppeared);
            return;
        }
    }

    protected boolean validate() {
        return true;
    }

    public abstract T createBean();
}
