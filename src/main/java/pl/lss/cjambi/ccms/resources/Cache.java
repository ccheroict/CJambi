/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.resources;

import java.util.List;
import pl.lss.cjambi.ccms.bean.Company;
import pl.lss.cjambi.ccms.bean.DiscountType;
import pl.lss.cjambi.ccms.bean.Office;
import pl.lss.cjambi.ccms.bean.OrderStatus;
import pl.lss.cjambi.ccms.bean.User;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;

/**
 *
 * @author ctran
 */
public class Cache {

    private static DbService db = DbServiceImpl.getInstance();
    private static User user;
    private static List<OrderStatus> orderStatuses = db.getOrderStatuses();
    private static List<DiscountType> discountTypes = db.getDiscountTypes();

    public static void setUser(User loggedUser) {
        user = loggedUser;
    }

    public static Company getUserCompany() {
        return user.office.company;
    }

    public static String getUserInitial() {
        return user.initial;
    }

    public static Office getUserOffice() {
        return user.office;
    }

    public static User getUser() {
        return user;
    }

    public static List<OrderStatus> getOrderStatuses() {
        return orderStatuses;
    }

    public static List<DiscountType> getDisCountTypes() {
        return discountTypes;
    }
}
