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
import pl.lss.cjambi.ccms.resources.Cache;

/**
 *
 * @author ctran
 */
@DatabaseTable
public class Order {

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
    public Date createdDate = new Date();
    @DatabaseField
    public Date lastChangedDate = new Date();
    @DatabaseField
    public Integer packQuantity = 0;
    @DatabaseField
    public Integer productQuantity = 0;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public DiscountType discountType;
    @DatabaseField
    public Double discountValue = 0.0;
    @DatabaseField
    public Double total = 0.0; //tong gia tri sp chua tinh giam gia
    @DatabaseField
    public Double value = 0.0; //da giam gia
    @ForeignCollectionField(eager = false, maxEagerLevel = 2)
    public ForeignCollection<Item> items;
    @DatabaseField(foreign = true)
    public Company company;
    @DatabaseField
    public Integer isActive = 1;

    public Order() {
        status = Cache.getOrderStatuses().get(0);
        discountType = Cache.getDisCountTypes().get(0);
        company = Cache.getUserCompany();
    }
}
