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

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(foreign = true)
    public Product product;
    @DatabaseField
    public int nPackRequired;
    @DatabaseField
    public int quantity;
    @DatabaseField
    public double price;
    @DatabaseField
    public double total;
    @DatabaseField(foreign = true)
    public Order order;

    public Item() {
        nPackRequired = 0;
        quantity = 0;
        price = 0;
        total = 0;
    }
}
