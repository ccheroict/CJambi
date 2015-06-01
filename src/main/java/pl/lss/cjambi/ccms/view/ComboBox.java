/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.QtPropertyReader;
import com.trolltech.qt.QtPropertyWriter;
import com.trolltech.qt.gui.QComboBox;
import java.util.List;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.StringUtils;

/**
 *
 * @author ctran
 */
public class ComboBox<T> extends QComboBox {

    private List<T> choices;
    private T state;

    public ComboBox(List<T> choices, String propName) {
        super();
        this.choices = choices;
        for (T obj : choices) {
            addItem(StringUtils.toStringOrNull(BeanUtils.getProperty(obj, propName)));
        }
        activatedIndex.connect(this, "onActiveIndex(Integer)");

    }

    @QtPropertyReader
    public T getState() {
        return state;
    }

    @QtPropertyWriter
    public void setState(T state) {
        Integer id = (Integer) BeanUtils.getProperty(state, "id");
        for (int i = 0; i < choices.size(); i++) {
            if (id.equals(BeanUtils.getProperty(choices.get(i), "id"))) {
                setCurrentIndex(i);
            }
        }
    }

    private void onActiveIndex(Integer row) {
        state = choices.get(row);
    }
}
