/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.gui.QWidget;

/**
 *
 * @author ctran
 */
public abstract class PageWidget extends VBoxWidget {

    public PageWidget() {
        super();
        addWidget(buildHeader());
        addWidget(buildContent());
        addWidget(buildFooter());
    }

    protected abstract QWidget buildHeader();

    protected abstract QWidget buildContent();

    protected abstract QWidget buildFooter();
}
