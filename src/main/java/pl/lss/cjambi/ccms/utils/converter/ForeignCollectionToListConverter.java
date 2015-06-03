/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

import com.j256.ormlite.dao.CloseableWrappedIterable;
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
public abstract class ForeignCollectionToListConverter implements Converter<ForeignCollection, List> {

    private static final DbService db = DbServiceImpl.getInstance();
    private Object bean;
    private String propName;

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
        dao.assignEmptyForeignCollection(parent.getClass(), childPropName);
        ForeignCollection collection = (ForeignCollection) BeanUtils.getProperty(bean, propName);
        collection.addAll(state);
        return collection;
    }

    @Override
    public List toPresentation(ForeignCollection data) throws Exception {
        List l = new ArrayList<>();
        CloseableWrappedIterable it = data.getWrappedIterable();
        while (it.iterator().hasNext()) {
            l.add(it.iterator().next());
        }
        it.close();
        return l;
    }
}