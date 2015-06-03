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
public class Catalog {

    public static final String NAME_FIELD = "name";

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String name;
    @DatabaseField(foreign = true)
    public Catalog parent;
    @DatabaseField(foreign = true)
    public Company company;

    public Catalog() {
    }
}
