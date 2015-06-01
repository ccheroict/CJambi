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

/**
 *
 * @author ctran
 */
@DatabaseTable
public class Order {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String code;
    @DatabaseField
    public Date createdDate;
    @DatabaseField
    public Date lastChangedDate;
    @DatabaseField
    public int packQuantity;
    @DatabaseField
    public int productQuantity;
    @DatabaseField(foreign = true)
    public DiscountType discount;
    @DatabaseField
    public double discountValue;
    @DatabaseField
    public double total;
    @ForeignCollectionField
    public ForeignCollection<Item> items;

    public Order() {
    }
}
