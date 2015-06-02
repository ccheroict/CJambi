/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import com.trolltech.qt.gui.QWidget;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.utils.converter.Converter;
import pl.lss.cjambi.ccms.utils.converter.DefaultConverter;

/**
 *
 * @author ctran
 */
public class Editor<T> {

    private static final Logger logger = Logger.getLogger(Editor.class);

    private T bean;
    private List<QWidget> widgets = new ArrayList<>();
    private List<String> propNames = new ArrayList<>();
    private List<Converter> converters = new ArrayList<>();
    private int nProp = 0;

    public void setBean(T bean) {
        this.bean = bean;
    }

    public void addMapping(QWidget widget, String propName) {
        addMapping(widget, propName, Cache.getInstance(DefaultConverter.class));
    }

    public void addMapping(QWidget widget, String propName, Converter converter) {
        nProp++;

        widgets.add(widget);
        propNames.add(propName);
        converters.add(converter);
    }

    public void flush() throws Exception {
        for (int i = 0; i < nProp; i++) {
            QWidget widget = widgets.get(i);
            String propName = propNames.get(i);
            Converter converter = converters.get(i);
            Object value = BeanUtils.getProperty(bean, propName);

            widget.setProperty("state", converter.toPresentation(value));
        }
    }

    public void commit() throws Exception {
        for (int i = 0; i < nProp; i++) {
            QWidget widget = widgets.get(i);
            String propName = propNames.get(i);
            Converter converter = converters.get(i);
            Object value = widget.property("state");

            BeanUtils.setProperty(bean, propName, converter.toData(value));
        }
    }

}
