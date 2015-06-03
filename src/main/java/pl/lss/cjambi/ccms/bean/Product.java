/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 *
 * @author ctran
 */
@DatabaseTable
public class Product {

    public static final String SELF = "self";
    public static final String CODE_FIELD = "code";
    public static final String SUPPLIER_FIELD = "supplier";
    public static final String SIZE_FIELD = "size";
    public static final String COLOUR_FIELD = "colour";
    public static final String PACK_SIZE_FIELD = "packSize";
    public static final String UNIT_FIELD = "unit";
    public static final String ORIGINAL_PRICE_FIELD = "originalPrice";
    public static final String DISCOUNT_VALUE_FIELD = "discountValue";
    public static final String DISCOUNT_TYPE_FIELD = "discountType";
    public static final String FINAL_PRICE_FIELD = "finalPrice";
    public static final String CATALOG_FIELD = "catalog";

    public Product self = this;

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String code;
    @DatabaseField(foreign = true)
    public Catalog catalog;
    @DatabaseField
    public String size;
    @DatabaseField
    public String colour;
    @DatabaseField
    public Integer packSize = 0;
    @DatabaseField
    public Double originalPrice = 0.0;
    @DatabaseField(foreign = true)
    public DiscountType discountType;
    @DatabaseField
    public Double discountValue = 0.0;
    @DatabaseField
    public Date promotionDateFrom;
    @DatabaseField
    public Date promotionDateTo;
    @DatabaseField
    public Double finalPrice = 0.0;
    @DatabaseField(foreign = true)
    public Supplier supplier;
    @DatabaseField(foreign = true)
    public Tax tax;
    @DatabaseField(foreign = true)
    public Unit unit;
    @DatabaseField(foreign = true)
    public Company company;

    public Product() {
    }

    public Double getPriceWithDiscount() {
        Double price = originalPrice;
        if (discountValue != null && discountValue != 0) {
            if (discountType.name.equals("%")) {
                price = (1 - discountValue / 100.0) * originalPrice;
            } else {
                price -= discountValue;
            }
        }
        return price;
    }

//    public double getPrice() {
//        Date now = new Date();
//        finalPrice = getPriceWithDiscount();
//        if (promotionDateFrom != null && promotionDateTo != null && promotionDateFrom.before(now) && now.before(promotionDateTo)) {
//            return finalPrice;
//        }
//        return originalPrice;
//    }
}
