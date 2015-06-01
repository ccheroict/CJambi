/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.gui.QApplication;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ctran
 */
public class LabelTest {

    public LabelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        QApplication.initialize(new String[0]);
    }

    @AfterClass
    public static void tearDownClass() {
        QApplication.execStatic();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testState() {
        Label label = new Label();
        label.setProperty("state", "BLA");
        System.out.println(label.property("state"));
        label.show();
    }
}
