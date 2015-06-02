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
public class Supplier {

    public static final String CODE_FIELD = "code";
    public static final String NAME_FIELD = "name";
    public static final String NOTE_FIELD = "note";

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String code;
    @DatabaseField
    public String name;
    @DatabaseField
    public String note;
    @DatabaseField(foreign = true)
    public Company company;

    public Supplier() {
    }
}
