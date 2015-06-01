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

    public static Object getProperty(Object bean, String property) {
        try {
            if (property == null) {
                return bean;
            }
            do {
                Class clazz = bean.getClass();
                SplittedString ss = new SplittedString(property, ".");
                Field prop = clazz.getField(ss.first);
                prop.setAccessible(true);
                bean = prop.get(bean);
                if (ss.last == null) {
                    return bean;
                }
                property = ss.last;
            } while (true);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            logger.error("getProperty", ex);
            return null;
        }
    }
}
