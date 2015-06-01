/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import com.trolltech.qt.gui.QAction;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;

/**
 *
 * @author ctran
 */
public class Utils {

    private static final Utils instance = new Utils();
    private static final Logger logger = Logger.getLogger(Utils.class);
    private static final DecimalFormat doubleFormatter = new DecimalFormat();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    static {
        doubleFormatter.setMinimumFractionDigits(2);
        doubleFormatter.setMaximumFractionDigits(2);
    }

    public static String toStringOrEmpty(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    public static String toStringOrNull(Object obj) {
        if (obj == null) {
            return null;
        } else {
            return String.valueOf(obj);
        }
    }

    public static List<String> splitToArray(String cmd, String seperator) {
        List<String> res = new ArrayList<>();
        SplittedString ss;
        while (cmd != null) {
            ss = new SplittedString(cmd, seperator);
            res.add(ss.first);
            cmd = ss.last;
        }
        return res;
    }

    public static String toStringOrDefault(Double defaultValue, Double data) {
        if (data == null) {
            return toStringOrEmpty(defaultValue);
        } else {
            return toStringOrEmpty(data);
        }
    }

    public static Date parseDate(String s) {
        try {
            return sdf.parse(s);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date lastChangedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static Double parseDouble(String s) {
        s = s.replaceAll("\\s+", "");
        s = s.replace(',', '.');
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static QAction createAddSupplierAction() {
        QAction addSupplierAction = new QAction(null);
        addSupplierAction.setIcon(IconResources.SUPPIER_ICON);
        addSupplierAction.setText(I18n.addNewSupplier);
        addSupplierAction.triggered.connect(instance, "onAddSupplierAction()");

        return addSupplierAction;
    }

    public static QAction createAddProductAction() {
        QAction addProductAction = new QAction(null);
        addProductAction.setIcon(IconResources.PRODUCT_ICON);
        addProductAction.setText(I18n.addNewProduct);
        addProductAction.triggered.connect(instance, "onAddProductAction()");
        return addProductAction;
    }

    public static QAction createAddOrderAction() {
        QAction addOrderAction = new QAction(null);
        addOrderAction.setIcon(IconResources.ORDER_ICON);
        addOrderAction.setText(I18n.addNewOrder);
        addOrderAction.triggered.connect(instance, "onAddOrderAction()");

        return addOrderAction;
    }

    public static QAction createAddClientAction() {
        QAction addClientAction = new QAction(null);
        addClientAction.setIcon(IconResources.CLIENT_ICON);
        addClientAction.setText(I18n.addNewClient);
        addClientAction.triggered.connect(instance, "onAddClientAction()");
        return addClientAction;
    }

    private void onAddProductAction() {
//        ProductEditDialog dialog = new ProductEditDialog(null);
//        dialog.exec();
//        refreshCentralWidget();
    }

    private void onAddOrderAction() {
//        OrderEditDialog dialog = new OrderEditDialog(null);
//        dialog.exec();
//        refreshCentralWidget();
    }

    private void onAddClientAction() {
//        ClientEditDialog dialog = new ClientEditDialog(null);
//        dialog.exec();
//        refreshCentralWidget();
    }

    private void onAddSupplierAction() {
//        SupplierEditDialog dialog = new SupplierEditDialog(null);
//        dialog.exec();
//        refreshCentralWidget();
    }
}
