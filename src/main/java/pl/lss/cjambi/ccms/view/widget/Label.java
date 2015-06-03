/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.gui.QLabel;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class Label extends QLabel implements HasState {

    @Override
    public String getState() {
        return text();
    }

    @Override
    public void setState(Object state) {
        if (state == null) {
            state = "";
        }
        setText(Utils.toStringOrEmpty(state));
    }
}
