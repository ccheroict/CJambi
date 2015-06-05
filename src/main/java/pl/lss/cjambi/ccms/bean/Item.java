/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author ctran
 */
@DatabaseTable
public class Item {

    private static final double EPS = 1e-6;

    public static final String PRODUCT_FIELD = "product";
    public static final String REQUIRED_PACK_FIELD = "requiredPack";
    public static final String QUANTITY_FIELD = "quantity";
    public static final String PRICE_FIELD = "price";
    public static final String TOTAL_FIELD = "value";
    public static final String SUPPLIER_CODE_FIELD = "product.supplier.code";
    public static final String PRODUCT_CODE_FIELD = "product.code";
    public static final String CATALOG_NAME_FIELD = "product.catalog.name";
    public static final String PACK_SIZE_FIELD = "product.packSize";

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public Product product;
    @DatabaseField
    public Integer requiredPack;
    @DatabaseField
    public Integer quantity;
    @DatabaseField
    public Double price;
    @DatabaseField
    public Double value;
    @DatabaseField(foreign = true)
    public Order order;

    public Item() {
    }

    public boolean equals(Item item) {
        if (item.product == null) {
            return false;
        }
        return (product.id.equals(item.product.id) && Math.abs(price - item.price) < EPS);
    }

    public void add(Item item) {
        requiredPack += item.requiredPack;
        quantity += item.quantity;
        value += item.value;
    }
}
