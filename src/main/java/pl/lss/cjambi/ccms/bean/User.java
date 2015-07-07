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
public class User {

    public static final String USERNAME_FIELD = "username";
    public static final String PASSWORD_FIELD = "password";

    @DatabaseField(generatedId = true)
    public Integer id;
    @DatabaseField
    public String name;
    @DatabaseField
    public String username;
    @DatabaseField
    public String password;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    public Office office; //where this user works
    @DatabaseField
    public String initial;
    @DatabaseField
    public String uuid;
    @DatabaseField
    public Integer isActive = 1;

    public User() {
    }
}
