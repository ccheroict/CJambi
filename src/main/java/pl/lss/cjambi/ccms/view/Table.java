/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.QtPropertyReader;
import com.trolltech.qt.QtPropertyWriter;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.converter.Converter;

/**
 *
 * @author ctran
 */
public class Table<T> extends QTableWidget {

    private static final Logger logger = Logger.getLogger(Table.class);
    private List<T> state;
    private List<String> propNames = new ArrayList<>();
    private List<Converter> converters = new ArrayList<>();
    private List<Integer> colAlignments = new ArrayList<>();

    public Table() {
    }

    @QtPropertyReader
    public List<T> getState() {
        return state;
    }

    @QtPropertyWriter
    public void setState(List<T> state) {
        this.state = state;
        refresh();
    }

    public void addColumn(String headerText, String propName, Converter converter) {
        addColumn(headerText, propName, converter, Qt.AlignmentFlag.AlignCenter);
    }

    public void addColumn(String header, String propName, Converter converter, Qt.AlignmentFlag alignmentFlag) {
        propNames.add(propName);
        converters.add(converter);
        colAlignments.add(alignmentFlag.value());

        int nCol = columnCount();
        QTableWidgetItem headerItem = new QTableWidgetItem(header);
        headerItem.setTextAlignment(alignmentFlag.value());
        headerItem.setToolTip(header);
        setHorizontalHeaderItem(nCol, headerItem);
        resizeColumnToContents(nCol);
        resizeColumnToContents(nCol - 1);
        horizontalHeader().setStretchLastSection(true);
        setColumnCount(nCol + 1);
    }

    private void refresh() {
        try {
            setRowCount(state.size());
            for (int row = 0; row < rowCount(); row++) {
                for (int col = 0; col < columnCount(); col++) {
                    Converter converter = converters.get(col);
                    String content = (String) converter.toPresentation(BeanUtils.getProperty(state.get(row), propNames.get(col)));
                    QTableWidgetItem item = new QTableWidgetItem(content);
                    item.setTextAlignment(colAlignments.get(col));
                    setItem(row, col, item);
                }
            }
        } catch (Exception ex) {
            logger.error("refresh", ex);
        }
    }
}
