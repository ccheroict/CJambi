/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.utils.converter.Converter;
import pl.lss.cjambi.ccms.utils.converter.DefaultConverter;
import pl.lss.cjambi.ccms.view.widget.HasState;

/**
 *
 * @author ctran
 */
public class Editor<T> {

    private static final Logger logger = Logger.getLogger(Editor.class);

    private T bean;
    private List<HasState> widgets = new ArrayList<>();
    private List<String> propNames = new ArrayList<>();
    private List<Converter> converters = new ArrayList<>();
    private Map<HasState, String> propNameInStates = new HashMap<>();
    private int nProp = 0;

    public void setBean(T bean) {
        this.bean = bean;
    }

    public void addMapping(HasState widget, String propName) {
        addMapping(widget, propName, null);
    }

    public void addMapping(HasState widget, String propName, String propNameInState) {
        addMapping(widget, propName, propNameInState, new DefaultConverter());
    }

    public void addMapping(HasState widget, String propName, String propNameInState, Converter converter) {
        nProp++;
        widgets.add(widget);
        propNames.add(propName);
        converters.add(converter);
        if (propNameInState != null) {
            propNameInStates.put(widget, propNameInState);
        }
    }

    public void flush() throws Exception {
        for (int i = 0; i < nProp; i++) {
            HasState widget = widgets.get(i);
            String propName = propNames.get(i);
            Converter converter = converters.get(i);
            Object value = BeanUtils.getProperty(bean, propName);

            widget.setState(converter.toPresentation(value));
        }
    }

    public void commit() throws Exception {
        for (int i = 0; i < nProp; i++) {
            HasState widget = widgets.get(i);
            String propName = propNames.get(i);
            Converter converter = converters.get(i);
            Object value = widget.getState();
            if (value != null) {
                value = BeanUtils.getProperty(value, propNameInStates.get(widget));
            }
            BeanUtils.setProperty(bean, propName, converter.toData(value));
        }
    }
}
