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
public class Office {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String name;
    @DatabaseField
    public String street;
    @DatabaseField
    public String zipcode;
    @DatabaseField
    public String tel;
    @DatabaseField
    public String fax;
    @DatabaseField
    public String email;
    @DatabaseField(foreign = true)
    public Company company; //this office is one of offices' 

    public Office() {
    }
}
