/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.gui.QComboBox;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class ComboBox<T> extends QComboBox implements HasState {

    private static final Logger logger = Logger.getLogger(ComboBox.class);
    private List<T> choices;
    private Object state;

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
        onActiveIndex(0);
    }

    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void setState(Object state) {
        if (state == null) {
            state = choices.get(0);
        }
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
