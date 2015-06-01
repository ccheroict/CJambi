/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import com.google.inject.Inject;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.trolltech.qt.gui.QWidget;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.db.DbService;

/**
 *
 * @author ctran
 */
public class Editor<T> {

    private static final Logger logger = Logger.getLogger(Editor.class);

    @Inject
    private static DbService db;

    private T bean;
    private Map<QWidget, String> widgetPropMap;

    public Editor(T bean) {
        this.bean = bean;
        widgetPropMap = new HashMap();
    }

    public void addMapping(QWidget widget, String propName) {
        widgetPropMap.put(widget, propName);
    }

    public void flush() throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        for (Entry<QWidget, String> entry : widgetPropMap.entrySet()) {
            QWidget widget = entry.getKey();
            String propName = entry.getValue();
            Object value = BeanUtils.getProperty(bean, propName);

            if (value instanceof ForeignCollection) {
                widget.setProperty("state", convertForeignCollectionToList((ForeignCollection) value));
            } else {
                widget.setProperty("state", value);
            }
        }
    }

    public void commit() throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        for (Entry<QWidget, String> entry : widgetPropMap.entrySet()) {
            QWidget widget = entry.getKey();
            String propName = entry.getValue();
            Object value = widget.property(propName);

            if (value instanceof List) {
                BeanUtils.setProperty(bean, propName, convertListToForeignCollection((List) value, propName));
            }
        }
    }

    private List<T> convertForeignCollectionToList(ForeignCollection collection) throws SQLException {
        List<T> l = new ArrayList<>();
        CloseableWrappedIterable it = collection.getWrappedIterable();
        while (it.iterator().hasNext()) {
            l.add((T) it.iterator().next());
        }
        it.close();
        return l;
    }

    private ForeignCollection<T> convertListToForeignCollection(List<T> l, String propName)
            throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Dao dao = db.getDao(bean.getClass());
        SplittedString ss = new SplittedString(propName, ".");
        String parentPropName = null;
        String childPropName = ss.first;
        if (ss.last != null) {
            parentPropName = ss.first;
            childPropName = ss.last;
        }
        Object parent = BeanUtils.getProperty(bean, parentPropName);
        dao.assignEmptyForeignCollection(parent.getClass(), childPropName);
        ForeignCollection collection = (ForeignCollection) BeanUtils.getProperty(bean, propName);
        collection.addAll(l);
        return collection;
    }
}
