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
    private Map<HasState, Object> widgetStates = new HashMap<>();
    private Map<String, Object> beanValues = new HashMap<>();
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
        flush(widgetPropNameMap.keySet().toArray(new HasState[widgetPropNameMap.size()]));
    }

    private void flush(HasState... widgets) {
        widgetStates.clear();
        try {
            for (int i = 0; i < widgets.length; i++) {
                HasState widget = widgets[i];

                String propName = widgetPropNameMap.get(widget);
                Object value = BeanUtils.getProperty(bean, propName);

                widgetStates.put(widget, widget.getState());
                setWidgetState(widget, value);
            }
        } catch (Exception ex) {
            for (Entry<HasState, Object> entry : widgetStates.entrySet()) {
                HasState widget = entry.getKey();
                Object state = entry.getValue();
                setWidgetState(widget, state);
            }
        }
    }

    public void commit() throws Exception {
        commit(widgetPropNameMap.keySet().toArray(new HasState[widgetPropNameMap.size()]));
    }

    private void commit(HasState... widgets) throws Exception {
        beanValues.clear();
        try {
            for (int i = 0; i < widgets.length; i++) {
                HasState widget = widgets[i];
                String propName = widgetPropNameMap.get(widget);

                if (SELF.equals(propName)) {
                    continue;
                }

                Object value = convertWidgetState(widget);
                beanValues.put(propName, BeanUtils.getProperty(bean, propName));
                BeanUtils.setProperty(bean, propName, value);
            }
        } catch (Exception ex) {
            for (Entry<String, Object> entry : beanValues.entrySet()) {
                String propName = entry.getKey();
                Object value = entry.getValue();
                BeanUtils.setProperty(bean, propName, value);
            }
        }
    }

    public Object convertWidgetState(HasState widget) throws Exception {
        Converter converter = getConverter(widget);
        Object value = widget.getState();
        return converter.toData(value);
    }

    public Converter getConverter(HasState widget) {
        return converters.get(widget);
    }

    public void setWidgetState(HasState widget, Object value) {
        Converter converter = getConverter(widget);
        try {
            widget.setState(converter.toPresentation(value));
        } catch (Exception ex) {
        }
    }
}
