/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.resources;

import pl.lss.cjambi.ccms.bean.Company;
import pl.lss.cjambi.ccms.bean.User;

/**
 *
 * @author ctran
 */
public class Cache {

    private static User user;

    public static void setUser(User loggedUser) {
        user = loggedUser;
    }

    public static Company getUserCompany() {
        return user.office.company;
    }

    public static String getUserInitial() {
        return user.initial;
    }

}
