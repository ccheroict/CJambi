/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.widget;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QAbstractItemView;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.Utils;
import pl.lss.cjambi.ccms.utils.converter.Converter;
import pl.lss.cjambi.ccms.utils.converter.DefaultConverter;

/**
 *
 * @author ctran
 */
public abstract class Table<T> extends QTableWidget implements HasState, Refreshable {

    private static final Logger logger = Logger.getLogger(Table.class);
    public static final long DEFAULT_SIZE = 20;

    protected List<T> state = new ArrayList<>();
    private List<String> propNames = new ArrayList<>();
    private List<Converter> converters = new ArrayList<>();
    private List<Integer> colAlignments = new ArrayList<>();

    public Table() {
        this(DEFAULT_SIZE);
    }

    public Table(long size) {
        super();
        doubleClicked.connect(this, "onDoubleClicked(QModelIndex)");
        setSelectionMode(QAbstractItemView.SelectionMode.ContiguousSelection);
        setEditTriggers(QAbstractItemView.EditTrigger.NoEditTriggers);
        setSelectionBehavior(QAbstractItemView.SelectionBehavior.SelectRows);
        setContextMenuPolicy(Qt.ContextMenuPolicy.CustomContextMenu);
        customContextMenuRequested.connect(this, "onCustomContextMenuRequested(QPoint)");
    }

    private void onCustomContextMenuRequested(QPoint point) {
        QPoint globalPoint = mapToGlobal(point);
        QMenu menu = new QMenu();
        QAction deleteAction = new QAction(null);
        deleteAction.setText(I18n.delete);
        deleteAction.triggered.connect(this, "onDeleteActionSelected()");
        menu.addAction(deleteAction);
        if (!selectionModel().selectedRows().isEmpty()) {
            if (selectionModel().selectedRows().size() == 1) {
                QAction printAction = new QAction(null);
                printAction.setText(I18n.print);
                printAction.triggered.connect(this, "onPrintActionSelected()");
                menu.addAction(printAction);
            }
            menu.exec(globalPoint);
        }
    }

    protected void onDeleteActionSelected() {
    }

    protected void onPrintActionSelected() {
        //NO-OP: Override it in extending tables
    }

    @Override
    public List<T> getState() {
        return state;
    }

    @Override
    public void setState(Object state) {
        this.state = (List<T>) state;
        refresh();
    }

    public void addColumn(String header, String propName) {
        addColumn(header, propName, new DefaultConverter());
    }

    public void addColumn(String header, String propName, Converter converter) {
        addColumn(header, propName, converter, Qt.AlignmentFlag.AlignCenter);
    }

    public void addColumn(String header, String propName, Converter converter, Qt.AlignmentFlag alignmentFlag) {
        propNames.add(propName);
        converters.add(converter);
        colAlignments.add(alignmentFlag.value());

        int nCol = columnCount();
        setColumnCount(nCol + 1);

        QTableWidgetItem headerItem = new QTableWidgetItem(header);
        headerItem.setText(header);
        headerItem.setTextAlignment(alignmentFlag.value());
        headerItem.setToolTip(header);
        setHorizontalHeaderItem(nCol, headerItem);
//        resizeColumnToContents(nCol);
//        resizeColumnToContents(nCol - 1);
//        horizontalHeader().setStretchLastSection(true);
    }

    @Override
    public void refresh() {
        try {
            setRowCount(state.size());
            for (int row = 0; row < rowCount(); row++) {
                for (int col = 0; col < columnCount(); col++) {
                    Converter converter = converters.get(col);
                    Object value = BeanUtils.getProperty(state.get(row), propNames.get(col));
                    String content = Utils.toStringOrEmpty(converter.toPresentation(value));
                    QTableWidgetItem item = new QTableWidgetItem(content);
                    item.setTextAlignment(colAlignments.get(col));
                    setItem(row, col, item);
                }
            }
            resizeColumnsToContents();
            horizontalHeader().setStretchLastSection(true);
        } catch (Exception ex) {
            logger.error("refresh", ex);
        }
    }

    private void onDoubleClicked(QModelIndex index) {
        T selected = state.get(index.row());
        showEditDialog(selected);
    }

    protected abstract void showEditDialog(T selected);
}
