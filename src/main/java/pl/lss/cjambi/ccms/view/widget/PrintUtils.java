/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.j256.ormlite.dao.ForeignCollection;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QPrintDialog;
import com.trolltech.qt.gui.QPrinter;
import com.trolltech.qt.gui.QPrinter.Unit;
import com.trolltech.qt.gui.QTextDocument;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.bean.Company;
import pl.lss.cjambi.ccms.bean.Item;
import pl.lss.cjambi.ccms.bean.Office;
import pl.lss.cjambi.ccms.bean.Order;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.resources.IconResources;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.Constants;
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.utils.converter.CurrencyToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.DateTimeToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.DoubleToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.ForeignCollectionToListConverter;
import pl.lss.cjambi.ccms.view.dialog.DialogErrorReporter;
import pl.lss.cjambi.ccms.view.dialog.ErrorReporter;

/**
 *
 * @author ctran
 */
public class PrintUtils {

    private static final Logger logger = Logger.getLogger(PrintUtils.class);
    private static final String PRINTER_NAME = "ccms";
    private static final int CODE_LENGTH = 6;
    private static final ErrorReporter reporter = DialogErrorReporter.getInstance();
    private static int firstPageItemNo = Integer.parseInt(Constants.mainProperties.getProperty("FIRST_PAGE_ITEM_NO", "19"));
    private static int nextPageItemNo = Integer.parseInt(Constants.mainProperties.getProperty("NEXT_PAGE_ITEM_NO", "22"));
    private static int orderItemsMarginBottom = Integer.parseInt(Constants.mainProperties.getProperty("ORDER_ITEM_MARGIN_BOTTOM", "36"));

    public static void print(Product product) {
        try {
            if (product.id == null) {
                reporter.error(I18n.mustSaveProductFirst);
                return;
            }
            PrintService psZebra = null;
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            for (int i = 0; i < services.length; i++) {
                if (PRINTER_NAME.equals(services[i].getName())) {
                    psZebra = services[i];
                    break;
                }
            }

            if (psZebra == null) {
                reporter.error(I18n.codePrinterNotFound);
                return;
            }

            String idStr = Utils.toStringOrEmpty(product.id);
            while (idStr.length() < CODE_LENGTH) {
                idStr = "0" + idStr;
            }
            DocPrintJob job = psZebra.createPrintJob();
            String supplierCode = Utils.toUTF8OrEmpty(BeanUtils.getProperty(product, Product.SUPPLIER_CODE_FIELD));
            String productPrice = Utils.toUTF8OrEmpty((new DoubleToStringConverter()).toPresentation(product.finalPrice));
            String packSize = Utils.toStringOrEmpty(BeanUtils.getProperty(product, Product.PACK_SIZE_FIELD));
            String productSize = Utils.toStringOrEmpty(BeanUtils.getProperty(product, Product.SIZE_FIELD));
            StringBuilder sb = new StringBuilder();
//            sb.append("^XA\n")
//                    .append("^FO10,10\n")
//                    .append("^BCN,60,Y,N,N\n").append("^FD" + idStr.toString() + "\n")
//                    .append("^FO10,80\n")
//                    .append("^ADN36,20\n").append("^FD" + supplierCode + " - " + productPrice + "\n")
//                    .append("^XZ\n");
            sb.append("^XA")
                    .append("^FO10,10").append("^BCN,60,Y,N,N").append("^FD" + idStr.toString() + "^FS")
                    .append("^FO220,20").append("^ADR,24,10^FD" + supplierCode + "^FS")
                    .append("^FO0,120").append("^ADN,24,10^FD" + product.size + "^FS")
                    .append("^FO0,140").append("^ADN,24,10^FD" + product.colour + "^FS")
                    .append("^FO100,100").append("^ADN,72,24^FD" + productPrice + "^FS")
                    //                    .append("^FO10,100").append("^ADN,24,10^FD" + supplierCode + " Cena: " + productPrice + "^FS")
                    //                    .append("^FO10,130").append("^ADN,24,10^FDOpak.:" + packSize + " Roz.: " + productSize + "^FS")
                    .append("^XZ");

            byte[] by = sb.toString().getBytes();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            // MIME type = "application/octet-stream",
            // print data representation class name = "[B" (byte array).
            Doc doc = new SimpleDoc(by, flavor, null);

            job.print(doc, null);
        } catch (Exception ex) {
            reporter.error(I18n.sorryErrorHasAppeared);
        }
    }

    public static void print(Order order) {
        QPrinter printer = new QPrinter();
        printer.setPageSize(QPrinter.PageSize.A4);
        printer.setOrientation(QPrinter.Orientation.Portrait);
        printer.setPageMargins(15, 10, 15, 10, Unit.Millimeter);
        printer.setFullPage(false);
//        printer.setOutputFileName("output.pdf");
//        printer.setOutputFormat(QPrinter.OutputFormat.PdfFormat); //you can use native format of system usin QPrinter::NativeFormat
        QPrintDialog dialog = new QPrintDialog(printer);
        dialog.setWindowTitle(I18n.printOrder);
        dialog.setWindowIcon(IconResources.APP_ICON);
        if (dialog.exec() != QDialog.DialogCode.Accepted.value()) {
            return;
        }
        QTextDocument doc = new QTextDocument();
        doc.setDefaultFont(new QFont("Times New Roman"));
        StringBuilder sb = new StringBuilder();
        Company company = Cache.getUserCompany();
        Office office = Cache.getUserOffice();
        try {
            doc.setDefaultStyleSheet(""
                    + "*{font-size: 16px;}"
                    + "#orderTable{font-size: medium; margin-top: 20px; margin-bottom: " + orderItemsMarginBottom + "px; border-style:solid;}"
                    + ".oddRow{background-color: #B5B5B5;}"
                    + ".item-info-cell{padding-top: 8px; padding-bottom: 8px;}"
                    + "tr{margin-top: 30px;}"
                    + "");
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<meta Content=\"Text/html;\">");
            sb.append("</head>");
            sb.append("<body>");

            sb.append("<table width=100%>");
            sb.append(" <tr>");
            sb.append("   <td>");
            sb.append("     <div style=\"font-size: 30px;\">" + company.name + "</div>");
//            sb.append("     <div style=\"font-size: 20px; font-weight:bold;\">" + office.name + "</div>");
//            sb.append("     <div>" + office.street + "</div>");
//            sb.append("     <div>" + office.zipcode + "</div>");
            sb.append("     <div>Tel: " + office.tel + "</div>");
            sb.append("     <div>" + office.fax + "</div>");
            sb.append("     <div>Email: " + office.email + "</div>");
            sb.append("     <div>Strona: www.tanieobuwie-g01-02.com.pl</div>");
            sb.append("   </td>");
//            sb.append("   <td style=\"padding-left: 200px; padding-top: 20px;\">");
//            sb.append("     <table>");
//            sb.append("      <tr>");
//            sb.append("        <td align=right>Czas: </td>");
//            sb.append("        <td align=left>" + Utils.toStringOrEmpty(new DateTimeToStringConverter().toPresentation(order.lastChangedDate)) + "</td>");
//            sb.append("      </tr>");
//            sb.append("      <tr>");
//            sb.append("        <td align=right>Reprezentant: </td>");
//            sb.append("        <td align=left>" + Cache.getUser().name + "</td>");
//            sb.append("      </tr>");
//            sb.append("     </table>");
//            sb.append("   </td>");
            sb.append(" </tr>");
            sb.append("</table>");

            List<Item> data = new ForeignCollectionToListConverter(order, Order.ITEMS_FIELD).toPresentation((ForeignCollection) BeanUtils.getProperty(order, Order.ITEMS_FIELD));
            Utils.removeInvalidItemFromOrder(data);
            sortOrderItemBySupplier(data);

            generateItemTablePage(sb, order.code, order.lastChangedDate, data, 0, firstPageItemNo);
            int id = firstPageItemNo;
            while (id < data.size()) {
                generateItemTablePage(sb, order.code, order.lastChangedDate, data, id, id + nextPageItemNo);
                id += nextPageItemNo;
            }
            if (data.size() >= firstPageItemNo) {
                sb.append("<br/>");
                sb.append("<div>Podsumowanie zamówienia - " + order.code + " : " + "<b style=\"font-size: 40px;\">" + order.packQuantity + "</b> opak. " + order.productQuantity + " par.</div>");
                sb.append("<div>Wartość: <b>" + new CurrencyToStringConverter(Constants.PLN).toPresentation(order.total) + "</b> </div>");
            }
            sb.append("</body>");
            sb.append("</html>");
            doc.setHtml(sb.toString());
        } catch (Exception ex) {
        }
        doc.print(printer);
    }

    private static void sortOrderItemBySupplier(List<Item> items) {
        Collections.sort(items, new Comparator<Item>() {

            @Override
            public int compare(Item i1, Item i2) {
                String s1 = "";
                String s2 = "";
                try {
                    s1 = Utils.toStringOrEmpty(BeanUtils.getProperty(i1, Item.SUPPLIER_CODE_FIELD));
                    s2 = Utils.toStringOrEmpty(BeanUtils.getProperty(i2, Item.SUPPLIER_CODE_FIELD));
                } catch (Exception ex) {
                }

                return s1.compareTo(s2);
            }
        });
    }

    private static void generateItemTablePage(StringBuilder sb, String code, Date lastChangedDate, List<Item> data, int startId, int endId) throws Exception {
        if (startId == 0) {
            sb.append("<div align=center style=\"margin-top: 10px;\">");
        } else {
            sb.append("<div align=center>");
        }
        sb.append(" <div style=\"font-size:30px;\">ZAMÓWIENIE Nr - " + code + "</div>");
        sb.append(" <div>Oryginalna/Kopia - Czas: " + Utils.toStringOrEmpty(new DateTimeToStringConverter().toPresentation(lastChangedDate)) + "</div>");
        sb.append("</div>");
        sb.append("<table id=\"orderTable\" border=\"1\" width=100% cellspacing=0 cellpadding=0>");
        sb.append(" <thead><tr>");
        sb.append("  <th>Lp.</th>");
        sb.append("  <th>Dostawca</th>");
        sb.append("  <th>Kod</th>");
        sb.append("  <th>Kolor</th>");
        sb.append("  <th>Pł.</th>");
        sb.append("  <th>Roz.</th>");
        sb.append("  <th>P</th>");
        sb.append("  <th>Op.</th>");
        sb.append("  <th>**Ilość**</th>");
        sb.append("  <th style=\"width: 100px;\">**Cena - ost.**</th>");
        sb.append("  <th>Wartość</th>");
        sb.append(" </tr></thead>");
        sb.append("<tbody>");

        int packQuantity = 0;
        int productQuantity = 0;
        double value = 0.0;
        for (int i = startId; i < endId; i++) {
            if (i >= data.size()) {
                break;
            }
            Item item = data.get(i);
//            if (i % 2 == 1) {
//                sb.append("<tr class=\"oddRow\">");
//            } else {
            sb.append("<tr>");
//            }
            sb.append(" <td class=\"item-info-cell\" align=center width=20px>" + (i + 1) + "</td>");
            sb.append(" <td class=\"item-info-cell\" align=center>" + Utils.toStringOrEmpty(BeanUtils.getProperty(item, Item.SUPPLIER_CODE_FIELD)) + "</td>");
            sb.append(" <td class=\"item-info-cell\" align=center>" + Utils.toStringOrEmpty(BeanUtils.getProperty(item, Item.PRODUCT_CODE_FIELD)) + "</td>");
            sb.append(" <td class=\"item-info-cell\" align=center>" + Utils.toStringOrEmpty(BeanUtils.getProperty(item, Item.PRODUCT_COLOUR_FIELD)) + "</td>");
            sb.append(" <td class=\"item-info-cell\" align=center>" + Utils.toStringOrEmpty(BeanUtils.getProperty(item, Item.PRODUCT_CATALOG_NAME_FIELD)) + "</td>");
            sb.append(" <td class=\"item-info-cell\" align=center>" + Utils.toStringOrEmpty(BeanUtils.getProperty(item, Item.PRODUCT_SIZE_FIELD)) + "</td>");
            packQuantity += item.requiredPack;
            sb.append(" <td class=\"item-info-cell\" align=center style=\"font-weight: bold;\">" + item.requiredPack + "</td>");
            sb.append(" <td class=\"item-info-cell\" align=center>" + Utils.toStringOrEmpty(BeanUtils.getProperty(item, Item.PRODUCT_PACK_SIZE_FIELD)) + "</td>");
            productQuantity += item.quantity;
            sb.append(" <td class=\"item-info-cell\" style=\"padding-left: 10px;\" align=left>" + item.quantity + "</td>");
            sb.append(" <td class=\"item-info-cell\" style=\"padding-left: 10px;\" align=left width=100px>" + Utils.toStringOrEmpty(item.price) + "</td>");
            value += item.value;
            sb.append(" <td class=\"item-info-cell\" align=center>" + Utils.toStringOrEmpty(Utils.round(item.value)) + "</td>");
            sb.append("</tr>");
        }

        sb.append("<tr style=\"font-weight: bold;\">");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
        sb.append(" <td align=right colspan=6>Razem :</td>");
        sb.append(" <td align=center style=\"font-size: x-large; font-weight:bold;\">" + packQuantity + "</td>");
        sb.append(" <td align=center>Op.</td>");
        sb.append(" <td align=center>" + productQuantity + "</td>");
        sb.append(" <td align=center>par</td>");

        sb.append(" <td align=center>" + new CurrencyToStringConverter(Constants.PLN).toPresentation(Utils.round(value)) + "</td>");
        sb.append("</tr>");
//            sb.append("<tr>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center></td>");
//            sb.append(" <td align=center>Rabat: </td>");
//            sb.append(" <td align=center>" + Utils.toStringOrEmpty(new DoubleToStringConverter().toPresentation(order.discountValue)) + " " + Utils.toStringOrEmpty(BeanUtils.getProperty(order, Order.DISCOUNT_TYPE_NAME_FIELD)) + "</td>");
//            sb.append("<tr>");
        sb.append("<tr style=\"font-weight: bold;\">");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
//        sb.append(" <td align=center></td>");
        sb.append(" <td align=right colspan=10>Wartość: </td>");
//            sb.append(" <td align=center>" + new CurrencyToStringConverter(Constants.PLN).toPresentation(order.value) + "</td>");
        sb.append(" <td align=center></td>");
        sb.append("</tr>");
        sb.append("</tbody></table>");
    }
}
