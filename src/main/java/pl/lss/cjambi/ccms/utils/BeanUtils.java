package pl.lss.cjambi.ccms.utils;

import java.lang.reflect.Field;
import org.apache.log4j.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ctran
 */
public class BeanUtils {

    private static final Logger logger = Logger.getLogger(BeanUtils.class);

    public static Object getProperty(Object bean, String property) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        try {
            if (bean == null || property == null) {
                return bean;
            }
            Object tmp = bean;
            do {
                Class clazz = tmp.getClass();
                SplittedString ss = new SplittedString(property, ".");
                Field prop = clazz.getField(ss.first);
                prop.setAccessible(true);
                tmp = prop.get(tmp);
                if (ss.last == null) {
                    return tmp;
                }
                property = ss.last;
            } while (true);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw ex;
        }
    }

    public static void setProperty(Object bean, String property, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        try {
            if (property == null) {
                bean = value;
                return;
            }
            Object tmp = bean;
            do {
                Class clazz = tmp.getClass();
                SplittedString ss = new SplittedString(property, ".");
                Field prop = clazz.getField(ss.first);
                prop.setAccessible(true);
                if (ss.last == null) {
                    prop.set(tmp, value);
                    return;
                }
                tmp = prop.get(bean);
                property = ss.last;
            } while (true);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            throw ex;
        }
    }
}
