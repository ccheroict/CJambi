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
@DatabaseTable(tableName = "discount_type")
public class DiscountType {

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String name;

    public DiscountType() {
    }
}
