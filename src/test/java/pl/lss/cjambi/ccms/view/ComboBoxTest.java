/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author ctran
 */
public class ComboBoxTest extends QDialog {

//    private static DbService db;
    private static ComboBoxTest instance;

    @BeforeClass
    public static void setUpClass() {
        QApplication.initialize(new String[0]);
//        db = Cache.getInstance(DbService.class);
        instance = new ComboBoxTest();
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
    public void testComboBoxUser() {
//        QHBoxLayout layout = new QHBoxLayout();
//        ComboBox<User> comboBox = new ComboBox<>(db.getUser(""), User.USERNAME_FIELD);
//        layout.addWidget(comboBox);
//        instance.setLayout(layout);

//        User state = comboBox.getState();
//        System.out.println(state.id);
    }

}
