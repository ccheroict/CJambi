/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;

/**
 *
 * @author ctran
 */
public class InformationDialog extends OkDialog {

    private static final InformationDialog instance = new InformationDialog();

    public static InformationDialog getInstance() {
        return instance;
    }

    private InformationType type = InformationType.INFO;
    private QLabel icon, message;

    private InformationDialog() {
        super();
        icon = new QLabel();
        message = new QLabel();
        build();
    }

    public InformationDialog setType(InformationType type) {
        this.type = type;
        icon.setPixmap(type.icon.pixmap(IconResources.ICON_48));
        return this;
    }

    public InformationDialog setMessage(String message) {
        this.message.setText(message);
        return this;
    }

    @Override
    protected QIcon getDialogIcon() {
        return type.icon;
    }

    @Override
    protected String getDialogTitle() {
        return type.title;
    }

    @Override
    protected QWidget buildContent() {
        QWidget w = new QWidget();
        QHBoxLayout layout = new QHBoxLayout(w);
        layout.addWidget(icon);
        layout.addWidget(message);
        layout.setSpacing(10);
        return w;
    }

    public enum InformationType {

        ERROR(IconResources.ERROR_ICON, I18n.error),
        INFO(IconResources.INFO_ICON, I18n.information),
        WARNING(IconResources.WARNING_ICON, I18n.warning);

        private QIcon icon;
        private String title;

        private InformationType(QIcon icon, String title) {
            this.icon = icon;
            this.title = title;
        }
    }
}
