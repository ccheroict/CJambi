/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QCompleter;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QStringListModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 * @param <T> type of state
 */
public abstract class SuggestBox<T> extends QLineEdit implements HasState {

    private static final Logger logger = Logger.getLogger(SuggestBox.class);
    private static final int DELAY_TIME = 300;

    private Class<T> type;
    private QTimer timer;
    private final String propName;
    private List<T> cache;
    private QStringListModel model;
    private QCompleter completer;

    public SuggestBox(Class<T> type, String propName) {
        super();
        this.type = type;
        this.propName = propName;

        timer = new QTimer();
        timer.setInterval(DELAY_TIME);
        timer.setSingleShot(true);
        timer.timeout.connect(this, "onTimerTimeout()");

        model = new QStringListModel();

        completer = new QCompleter();
        completer.setModel(model);
        completer.setCompletionMode(QCompleter.CompletionMode.UnfilteredPopupCompletion);
        completer.activatedIndex.connect(this, "onCompleterActivatedIndex(QModelIndex)");
        setCompleter(completer);

        textEdited.connect(this, "onTextEdited(String)");
    }

    @Override
    public Object getState() {
        return text();
    }

    @Override
    public void setState(Object state) {
        try {
            setText(Utils.toStringOrEmpty(state));
        } catch (Exception ex) {
            logger.error("setState", ex);
        }
    }

    private void onTextEdited(String query) {
        if (timer.isActive()) {
            timer.stop();
        }
        timer.start();
    }

    private void onCompleterActivatedIndex(QModelIndex index) {
        int row = index.row();
        try {
            setState(BeanUtils.getProperty(cache.get(row), propName));
        } catch (Exception ex) {
        }
    }

    private void onTimerTimeout() {
        cache = fetchData();
        model.setStringList(convertToStringList(cache));
        completer.complete();
    }

    private List<String> convertToStringList(List<T> cache) {
        List<String> l = new ArrayList<>();
        try {
            for (T obj : cache) {
                l.add(Utils.toStringOrEmpty(BeanUtils.getProperty(obj, propName)));
            }
        } catch (Exception ex) {
            logger.error("convertToStringList", ex);
        }
        return l;
    }

    public boolean isValidState() {
        return (getState() != null && (getState().getClass() == type));
    }

    public abstract List<T> fetchData();
}
