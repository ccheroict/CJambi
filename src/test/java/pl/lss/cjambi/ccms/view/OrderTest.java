/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.db.DbService;

/**
 *
 * @author ctran
 */
public class OrderTest {

    private static DbService db;

    public static void main(String[] args) {
//        db = Cache.getInstance(DbService.class);
        db.getOrder(new Filter());
    }
}
