/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.QtPropertyReader;
import com.trolltech.qt.QtPropertyWriter;
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
public abstract class SuggestBox<T> extends QLineEdit {

    private static final Logger logger = Logger.getLogger(SuggestBox.class);

    private static final int DELAY_TIME = 300;
    private QTimer timer;
    private T state;
    private final String propName;
    private List<T> cache;
    private QStringListModel model;
    private QCompleter completer;
    private String query;

    public SuggestBox(String propName) {
        super();
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

    @QtPropertyReader
    public T getState() {
        return state;
    }

    @QtPropertyWriter
    public void setState(T state) {
        try {
            this.state = state;
            setText(Utils.toStringOrEmpty(BeanUtils.getProperty(state, propName)));
        } catch (Exception ex) {
            logger.error("setState", ex);
        }
    }

    private void onTextEdited(String query) {
        if (timer.isActive()) {
            timer.stop();
        }
        this.query = query;
        timer.start();
    }

    private void onCompleterActivatedIndex(QModelIndex index) {
        int row = index.row();
        setState(cache.get(row));
    }

    private void onTimerTimeout() {
        cache = fetchData(query);
        model.setStringList(convertToStringList(cache));
        completer.complete();
    }

    private List<String> convertToStringList(List<T> cache) {
        List<String> l = new ArrayList<>();
        try {
            for (T obj : cache) {
                l.add(Utils.toStringOrNull(BeanUtils.getProperty(obj, propName)));
            }
        } catch (Exception ex) {
            logger.error("convertToStringList", ex);
        }
        return l;
    }

    public abstract List<T> fetchData(String query);
}
