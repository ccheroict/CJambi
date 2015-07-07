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
@DatabaseTable(tableName = "order_status")
public class OrderStatus {

    public static final String NAME_FIELD = "name";

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String name;

    public OrderStatus() {
    }
}
