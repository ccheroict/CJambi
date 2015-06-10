/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view.dialog;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QApplication;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.lss.ccms.cjambi.bean.Product;

/**
 *
 * @author ctran
 */
public class ProductEditDialogTest {

    private static ProductEditDialog instance;

    public ProductEditDialogTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        QApplication.initialize(new String[0]);
        instance = new ProductEditDialog(new Product());
//        db = Cache.getInstance(DbService.class);
    }

    @AfterClass
    public static void tearDownClass() {
        QTimer.singleShot(0, instance, "close()");
        QApplication.execStatic();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() {
        instance.setBean(new Product());
//        instance.exec();
    }
    /**
     * Test of getInstance method, of class ProductEditDialog.
     */
}
