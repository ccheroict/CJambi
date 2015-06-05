/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.table;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.Constants;
import pl.lss.cjambi.ccms.utils.converter.CurrencyToStringConverter;
import pl.lss.cjambi.ccms.utils.converter.IntegerToStringConverter;
import pl.lss.cjambi.ccms.view.HBoxWidget;
import pl.lss.cjambi.ccms.view.PageWidget;
import pl.lss.cjambi.ccms.view.VBoxWidget;
import pl.lss.cjambi.ccms.view.dialog.ProductEditDialog;
import pl.lss.cjambi.ccms.view.widget.Table;

/**
 *
 * @author ctran
 */
public class ProductTable extends PageWidget {

    private static final DbService db = DbServiceImpl.getInstance();

    private Table<Product> table;
    private Filter filter = new Filter();
    private Pager pager;
    private long maxPage;
    private QLabel total;

    public ProductTable() {
        super();
    }

    @Override
    protected QWidget buildHeader() {
        HBoxWidget header = new HBoxWidget();
        header.addWidget(new QLabel(I18n.productList));
        return header;
    }

    @Override
    protected QWidget buildContent() {
        table = new Table<Product>() {

            @Override
            protected void showEditDialog(Product selected) {
//                SupplierEditDialog dialog = SupplierEditDialog.getInstance();
                ProductEditDialog dialog = new ProductEditDialog(selected);
                dialog.exec();
            }
        };
        table.addColumn(I18n.productCode, Product.CODE_FIELD);
        table.addColumn(I18n.catalog, Product.CATALOG_NAME_FIELD);
        table.addColumn(I18n.supplier, Product.SUPPLIER_CODE_FIELD);
        table.addColumn(I18n.packSize, Product.PACK_SIZE_FIELD, new IntegerToStringConverter());
        table.addColumn(I18n.finalPrice, Product.ORIGINAL_PRICE_FIELD, new CurrencyToStringConverter(Constants.PLN), Qt.AlignmentFlag.AlignRight);
        table.addColumn(I18n.note, Product.NOTE_FIELD);
        return table;
    }

    @Override
    protected QWidget buildFooter() {
        VBoxWidget footer = new VBoxWidget();
        footer.addWidget(total = new QLabel(I18n.total + ": "));
        pager = new Pager(Table.DEFAULT_SIZE) {

            @Override
            protected long getMaxPage() {
                long cnt = db.countProduct(filter);
                total.setText(I18n.total + ": " + cnt);
                return ((cnt - 1) / Table.DEFAULT_SIZE + 1);
            }

            @Override
            protected void fetchDataAndRefreshTable() {
                filter.pageSize = pageSize;
                filter.pageNum = pageNum - 1;
                table.setState(db.getProduct(filter));
            }
        };
        footer.addWidget(pager);
        return footer;
    }

    public void refresh() {
        pager.onRefreshBtnCLicked();
    }
}