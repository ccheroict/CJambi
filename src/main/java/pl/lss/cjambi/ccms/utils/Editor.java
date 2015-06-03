/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
    private static final String SELF = "self";

    private T bean;
    private Map<HasState, String> widgetPropNameMap = new HashMap<>();
    private Map<HasState, Converter> converters = new HashMap<>();
    private int nProp = 0;

    public void setBean(T bean) {
        this.bean = bean;
    }

    public void addMapping(HasState widget, String propName) {
        addMapping(widget, propName, new DefaultConverter());
    }

    public void addMapping(HasState widget, String propName, Converter converter) {
        nProp++;
        widgetPropNameMap.put(widget, propName);
        converters.put(widget, converter);
    }

    public void flush() {
        try {
            for (Entry<HasState, String> entry : widgetPropNameMap.entrySet()) {
                HasState widget = entry.getKey();
                flush(widget);
            }
        } catch (Exception ex) {
            //Try flush simple prop -> do not catch Exception here
        }
    }

    public void flush(HasState widget) throws Exception {
        String propName = widgetPropNameMap.get(widget);
        Converter converter = getConverter(widget);
        Object value = BeanUtils.getProperty(bean, propName);

        widget.setState(converter.toPresentation(value));
    }

    public void commit() {
        try {
            for (Entry<HasState, String> entry : widgetPropNameMap.entrySet()) {
                HasState widget = entry.getKey();
                commit(widget);
            }
        } catch (Exception ex) {
            //Try commit simple prop -> do not catch Exception here
        }
    }

    public void commit(HasState widget) throws Exception {
        String propName = widgetPropNameMap.get(widget);
        if (SELF.equals(propName)) {
            return;
        }
        Converter converter = getConverter(widget);
        Object value = widget.getState();
        BeanUtils.setProperty(bean, propName, converter.toData(value));
    }

    public Converter getConverter(HasState widget) {
        return converters.get(widget);
    }
}
