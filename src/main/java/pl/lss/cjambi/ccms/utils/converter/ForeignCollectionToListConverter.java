/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import java.util.ArrayList;
import java.util.List;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.utils.BeanUtils;
import pl.lss.cjambi.ccms.utils.SplittedString;

/**
 *
 * @author ctran
 */
public class ForeignCollectionToListConverter implements Converter<ForeignCollection, List> {

    private static final DbService db = DbServiceImpl.getInstance();
    private Object bean;
    private String propName;

    /**
     *
     * @param bean bean which contains ForeignCollection
     * @param propName  ForeignCollection property name
     */
    public ForeignCollectionToListConverter(Object bean, String propName) {
        this.bean = bean;
        this.propName = propName;
    }

    @Override
    public ForeignCollection toData(List state) throws Exception {
        Dao dao = db.getDao(bean.getClass());
        SplittedString ss = new SplittedString(propName, ".");
        String parentPropName = null;
        String childPropName = ss.first;
        if (ss.last != null) {
            parentPropName = ss.first;
            childPropName = ss.last;
        }
        Object parent = BeanUtils.getProperty(bean, parentPropName);
        ForeignCollection collection = (ForeignCollection) BeanUtils.getProperty(bean, propName);
        if (collection == null) {
            dao.assignEmptyForeignCollection(parent, childPropName);
            collection = (ForeignCollection) BeanUtils.getProperty(bean, propName);
        } else {
            collection.clear();
        }
        collection.addAll(state);
        return collection;
    }

    @Override
    public List toPresentation(ForeignCollection data) throws Exception {
        List l = new ArrayList<>();
        if (data != null) {

            CloseableIterator it = data.closeableIterator();
            try {
                while (it.hasNext()) {
                    l.add(it.next());
                }
            } finally {
                it.close();
            }
        }
        return l;
    }
}
