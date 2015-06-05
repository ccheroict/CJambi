/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.gui.QLineEdit;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class LineEdit extends QLineEdit implements HasState {

    @Override
    public String getState() {
        return text();
    }

    @Override
    public void setState(Object state) {
        setText(Utils.toStringOrEmpty(state));
    }
}
