/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import pl.lss.cjambi.ccms.view.widget.ComboBox;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.lss.cjambi.ccms.bean.User;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.resources.Cache;

/**
 *
 * @author ctran
 */
public class ComboBoxTest extends QDialog {

    private static DbService db;
    private static ComboBoxTest instance;

    @BeforeClass
    public static void setUpClass() {
        QApplication.initialize(new String[0]);
        db = Cache.getInstance(DbService.class);
        instance = new ComboBoxTest();
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
    public void testComboBoxUser() {
        QHBoxLayout layout = new QHBoxLayout();
        ComboBox<User> comboBox = new ComboBox<>(db.getUser(""), User.USERNAME_FIELD);
        layout.addWidget(comboBox);
        instance.setLayout(layout);

        User state = comboBox.getState();
        System.out.println(state.id);
    }

}
