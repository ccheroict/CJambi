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
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class ComboBox<T> extends QComboBox {

    private static final Logger logger = Logger.getLogger(ComboBox.class);
    private List<T> choices;
    private T state;

    public ComboBox(List<T> choices, String propName) {
        super();
        this.choices = choices;
        try {
            for (T obj : choices) {
                addItem(Utils.toStringOrNull(BeanUtils.getProperty(obj, propName)));
            }
        } catch (Exception ex) {
            logger.error("ComboBox", ex);
        }

        activatedIndex.connect(this, "onActiveIndex(Integer)");

    }

    @QtPropertyReader
    public T getState() {
        return state;
    }

    @QtPropertyWriter
    public void setState(T state) {
        try {
            Integer id = (Integer) BeanUtils.getProperty(state, "id");
            for (int i = 0; i < choices.size(); i++) {
                if (id.equals(BeanUtils.getProperty(choices.get(i), "id"))) {
                    setCurrentIndex(i);
                }
            }
        } catch (Exception ex) {
            logger.error("setState", ex);
        }
    }

    private void onActiveIndex(Integer row) {
        state = choices.get(row);
    }
}
