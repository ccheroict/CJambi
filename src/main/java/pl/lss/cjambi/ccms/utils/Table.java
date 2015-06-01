/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import com.trolltech.qt.QtPropertyReader;
import com.trolltech.qt.QtPropertyWriter;
import com.trolltech.qt.gui.QTableWidget;
import com.trolltech.qt.gui.QTableWidgetItem;
import java.util.ArrayList;
import java.util.List;
import pl.lss.cjambi.ccms.utils.converter.Converter;

/**
 *
 * @author ctran
 */
public class Table<T> extends QTableWidget {

    private List<T> state;
    private List<String> propNames = new ArrayList<>();
    private List<Converter> converters = new ArrayList<>();

    @QtPropertyReader
    public List<T> getState() {
        return state;
    }

    @QtPropertyWriter
    public void setState(List<T> state) {
        this.state = state;
        refresh();
    }

    public void addColumn(String propName, Converter converter, String header) {
        propNames.add(propName);
        converters.add(converter);
        setColumnCount(propNames.size());
        horizontalHeaderItem(propNames.size() - 1).setText(header);
    }

    private void refresh() {
        setRowCount(state.size());
        for (int row = 0; row < rowCount(); row++) {
            for (int col = 0; col < columnCount(); col++) {
                Converter converter = converters.get(col);
                String content = (String) converter.toPresentation(BeanUtils.getProperty(state.get(row), propNames.get(col)));
                QTableWidgetItem item = new QTableWidgetItem(content);
                setItem(row, col, item);
            }
        }
    }
}
