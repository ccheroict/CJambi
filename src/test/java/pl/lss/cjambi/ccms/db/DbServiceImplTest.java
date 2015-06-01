/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.db;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Order;
import pl.lss.cjambi.ccms.resources.Cache;

/**
 *
 * @author ctran
 */
public class DbServiceImplTest {

    private static DbService db;

    public DbServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        db = Cache.getInstance(DbService.class);
        Order order = db.getOrder(new Filter()).get(0);
        db.createOrUpdateOrder(order);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetOrder() {
        db.getOrder(new Filter());
    }
}
