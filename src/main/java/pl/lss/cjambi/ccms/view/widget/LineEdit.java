/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.QtPropertyReader;
import com.trolltech.qt.QtPropertyWriter;
import com.trolltech.qt.gui.QLineEdit;

/**
 *
 * @author ctran
 */
public class LineEdit extends QLineEdit {

    @QtPropertyReader
    public String getState() {
        return text();
    }

    @QtPropertyWriter
    public void setState(String text) {
        setText(text);
    }
}
