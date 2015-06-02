/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.table;

import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.view.HBoxWidget;
import pl.lss.cjambi.ccms.view.PageWidget;
import pl.lss.cjambi.ccms.view.VBoxWidget;
import pl.lss.cjambi.ccms.view.dialog.SupplierEditDialog;
import pl.lss.cjambi.ccms.view.widget.Table;

/**
 *
 * @author ctran
 */
public class SupplierTable extends PageWidget {

    private static final SupplierTable instance = new SupplierTable();

    public static SupplierTable getInstance() {
        return instance;
    }

    private static final DbService db = DbServiceImpl.getInstance();

    private Table<Supplier> table;
    private Filter filter = new Filter();
    private Pager pager;
    private long maxPage;
    private QLabel total;

    private SupplierTable() {
        super();
    }

    @Override
    protected QWidget buildHeader() {
        HBoxWidget header = new HBoxWidget();
        header.addWidget(new QLabel(I18n.supplierList));
        return header;
    }

    @Override
    protected QWidget buildContent() {
        table = new Table<Supplier>() {

            @Override
            protected void showEditDialog(Supplier selected) {
                SupplierEditDialog dialog = SupplierEditDialog.getInstance();
                dialog.setBean(selected);
                dialog.exec();
            }
        };
        table.addColumn(I18n.supplierCode, Supplier.CODE_FIELD);
        table.addColumn(I18n.supplierName, Supplier.NAME_FIELD);
        table.addColumn(I18n.note, Supplier.NOTE_FIELD);
        return table;
    }

    @Override
    protected QWidget buildFooter() {
        VBoxWidget footer = new VBoxWidget();
        footer.addWidget(total = new QLabel(I18n.total + ": "));
        pager = new Pager(Table.DEFAULT_SIZE) {

            @Override
            protected long getMaxPage() {
                long cnt = db.countSupplier(filter);
                total.setText(I18n.total + ": " + cnt);
                return ((cnt - 1) / Table.DEFAULT_SIZE + 1);
            }

            @Override
            protected void fetchData() {
                filter.pageSize = pageSize;
                filter.pageNum = pageNum - 1;
                table.setState(db.getSupplier(filter));
            }
        };
        footer.addWidget(pager);
        return footer;
    }

    public void refresh() {
        pager.onRefreshBtnCLicked();
    }
}
