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
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.StringUtils;

/**
 *
 * @author ctran
 * @param <T> type of state
 */
public abstract class SuggestBox<T> extends QLineEdit {

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
        this.state = state;
        setText(StringUtils.toStringOrEmpty(BeanUtils.getProperty(state, propName)));
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
        for (T obj : cache) {
            l.add(StringUtils.toStringOrNull(BeanUtils.getProperty(obj, propName)));
        }
        return l;
    }

    public abstract List<T> fetchData(String query);
}
