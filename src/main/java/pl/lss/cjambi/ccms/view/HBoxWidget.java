/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QSpacerItem;
import com.trolltech.qt.gui.QWidget;

/**
 *
 * @author ctran
 */
public class HBoxWidget extends QWidget {

    protected QHBoxLayout layout = new QHBoxLayout(this);

    public void addWidget(QWidget widget) {
        layout.addWidget(widget);
    }

    public void addWidget(QWidget widget, int stretch, Qt.AlignmentFlag... alignment) {
        layout.addWidget(widget, stretch, alignment);
    }

    public void addSpacerItemToStretchWidget() {
        layout.addSpacerItem(new QSpacerItem(0, 0, QSizePolicy.Policy.Expanding));
    }
}
