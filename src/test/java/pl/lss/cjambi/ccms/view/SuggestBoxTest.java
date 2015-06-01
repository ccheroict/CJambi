/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import java.util.List;
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
public class SuggestBoxTest extends QDialog {

    private static DbService db;
    private static SuggestBoxTest instance;

    @BeforeClass
    public static void setUpClass() {
        QApplication.initialize(new String[0]);
        db = Cache.getInstance(DbService.class);
        instance = new SuggestBoxTest();
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

    /**
     * Test of getState method, of class SuggestBox.
     */
    @Test
    public void testSuggestUser() {
        QHBoxLayout layout = new QHBoxLayout();
        SuggestBox<User> suggestBox = new SuggestBox(User.USERNAME_FIELD) {

            @Override
            public List fetchData(String query) {
                return db.getUser(query);
            }
        };
        layout.addWidget(suggestBox);
        instance.setLayout(layout);
        System.out.println(instance.exec());

        User state = suggestBox.getState();
        System.out.println(state.id);
    }

}
