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

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String code;
    @DatabaseField
    public String size;
    @DatabaseField
    public String colour;
    @DatabaseField
    public int packSize;
    @DatabaseField
    public double originalPrice;
    @DatabaseField(foreign = true)
    public DiscountType discountType;
    @DatabaseField
    public double discountValue;
    @DatabaseField
    public Date promotionDateFrom;
    @DatabaseField
    public Date promotionDateTo;
    @DatabaseField
    public double finalPrice;
    @DatabaseField(foreign = true)
    public Supplier supplier;
    @DatabaseField(foreign = true)
    public Tax tax;
    @DatabaseField(foreign = true)
    public Unit unit;

    public Product() {
        packSize = 0;
        originalPrice = 0;
        discountValue = 0;
        finalPrice = 0;
    }

    public double getPrice() {
        Date now = new Date();
        if (promotionDateFrom != null && promotionDateTo != null && promotionDateFrom.before(now) && now.before(promotionDateTo)) {
            return finalPrice;
        }
        return originalPrice;
    }
}
