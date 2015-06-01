/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.QtPropertyReader;
import com.trolltech.qt.QtPropertyWriter;
import com.trolltech.qt.gui.QLabel;

/**
 *
 * @author ctran
 */
public class Label extends QLabel {

    @QtPropertyReader
    public String getState() {
        return text();
    }

    @QtPropertyWriter
    public void setState(String state) {
        setText(state);
    }
}
