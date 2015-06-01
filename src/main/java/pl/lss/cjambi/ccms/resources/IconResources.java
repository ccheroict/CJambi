/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.resources;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QIcon;

/**
 *
 * @author ctran
 */
public class IconResources {

    public static final String IMAGES_CLASSPATH = "classpath:images/";

    public static final QSize ICON_16 = new QSize(16, 16);
    public static final QSize ICON_32 = new QSize(32, 32);
    public static final QSize ICON_48 = new QSize(48, 48);
    public static final QSize ICON_64 = new QSize(64, 64);

    public static final QIcon ACTION_ICON = new QIcon(IMAGES_CLASSPATH + "action_icon.png");
    public static final QIcon CANCEL_ICON = new QIcon(IMAGES_CLASSPATH + "cancel_icon.png");
    public static final QIcon FIRST_BUTTON_ICON = new QIcon(IMAGES_CLASSPATH + "arrow_first.png");
    public static final QIcon PREV_BUTTON_ICON = new QIcon(IMAGES_CLASSPATH + "arrow_left.png");
    public static final QIcon NEXT_BUTTON_ICON = new QIcon(IMAGES_CLASSPATH + "arrow_right.png");
    public static final QIcon LAST_BUTTON_ICON = new QIcon(IMAGES_CLASSPATH + "arrow_last.png");
    public static final QIcon REFRESH_BUTTON_ICON = new QIcon(IMAGES_CLASSPATH + "refresh.png");
    public static final QIcon INFO_ICON = new QIcon(IMAGES_CLASSPATH + "info.png");
    public static final QIcon WARNING_ICON = new QIcon(IMAGES_CLASSPATH + "warning.png");
    public static final QIcon ERROR_ICON = new QIcon(IMAGES_CLASSPATH + "error.png");
    public static final QIcon SUPPIER_ICON = new QIcon(IMAGES_CLASSPATH + "supplier_icon.png");
    public static final QIcon APP_ICON = new QIcon(IMAGES_CLASSPATH + "app_icon.png");
    public static final QIcon PRODUCT_ICON = new QIcon(IMAGES_CLASSPATH + "product.png");
    public static final QIcon ORDER_ICON = new QIcon(IMAGES_CLASSPATH + "order.png");
    public static final QIcon DELETE_ICON = new QIcon(IMAGES_CLASSPATH + "delete.png");
    public static final QIcon CLIENT_ICON = new QIcon(IMAGES_CLASSPATH + "client.png");

}
