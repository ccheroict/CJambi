/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.QtPropertyReader;
import com.trolltech.qt.QtPropertyWriter;
import com.trolltech.qt.gui.QPlainTextEdit;

/**
 *
 * @author ctran
 */
public class PlainTextEdit extends QPlainTextEdit {

    @QtPropertyReader
    public String getState() {
        return toPlainText();
    }

    @QtPropertyWriter
    public void setState(String text) {
        setPlainText(text);
    }
}
