/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

/**
 *
 * @author ctran
 */
public class VBoxWidget extends QWidget {

    protected QVBoxLayout layout = new QVBoxLayout(this);

    public void addWidget(QWidget widget) {
        layout.addWidget(widget);
    }
}
