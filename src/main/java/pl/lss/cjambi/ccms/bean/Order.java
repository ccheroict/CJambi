/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import pl.lss.cjambi.ccms.utils.Printable;

/**
 *
 * @author ctran
 */
@DatabaseTable
public class Order implements Printable {

    public static final String CODE_FIELD = "code";
    public static final String STATUS_FIELD = "status";
    public static final String STATUS_NAME_FIELD = "status.name";
    public static final String CREATED_DATE_FIELD = "createdDate";
    public static final String ITEMS_FIELD = "items";
    public static final String PACK_QUANTITY_FIELD = "packQuantity";
    public static final String PRODUCT_QUANTITY_FIELD = "productQuantity";
    public static final String TOTAL_FIELD = "total";
    public static final String DISCOUNT_VALUE_FIELD = "discountValue";
    public static final String DISCOUNT_TYPE_FIELD = "discountType";
    public static final String DISCOUNT_TYPE_NAME_FIELD = "discountType.name";
    public static final String VALUE_FIELD = "value";

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String code;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public OrderStatus status;
    @DatabaseField
    public Date createdDate;
    @DatabaseField
    public Date lastChangedDate;
    @DatabaseField
    public Integer packQuantity;
    @DatabaseField
    public Integer productQuantity;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public DiscountType discountType;
    @DatabaseField
    public Double discountValue;
    @DatabaseField
    public Double total;
    @DatabaseField
    public Double value;
    @ForeignCollectionField(eager = true, maxEagerLevel = 2)
    public ForeignCollection<Item> items;
    @DatabaseField(foreign = true)
    public Company company;
    @DatabaseField
    public Integer isActive;

    public Order() {
        createdDate = new Date();
    }

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
