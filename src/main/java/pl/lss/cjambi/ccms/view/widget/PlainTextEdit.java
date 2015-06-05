/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.gui.QPlainTextEdit;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class PlainTextEdit extends QPlainTextEdit implements HasState {

    @Override
    public String getState() {
        return toPlainText();
    }

    @Override
    public void setState(Object state) {
        setPlainText(Utils.toStringOrEmpty(state));
    }
}
