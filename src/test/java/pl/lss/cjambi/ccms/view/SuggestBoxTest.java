/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.view;

import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QHBoxLayout;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.lss.ccms.cjambi.bean.User;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.view.widget.SuggestBox;

/**
 *
 * @author ctran
 */
public class SuggestBoxTest extends QDialog {

    private static DbService db = DbServiceImpl.getInstance();
    private static SuggestBoxTest instance;

    @BeforeClass
    public static void setUpClass() {
        QApplication.initialize(new String[0]);
//        db = Cache.getInstance(DbService.class);
        instance = new SuggestBoxTest();
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

    /**
     * Test of getState method, of class SuggestBox.
     */
    @Test
    public void testSuggestUser() {
        QHBoxLayout layout = new QHBoxLayout();
        SuggestBox<User> suggestBox = new SuggestBox(User.class, User.USERNAME_FIELD) {

            @Override
            public List fetchData() {
                return db.getUser(text());
            }
        };
        suggestBox.setStyleSheet("QLineEdit{border: 1px solid red;}");
        layout.addWidget(suggestBox);
        instance.setLayout(layout);
//        instance.exec();
//        User state = (User) suggestBox.getState();
//        System.out.println(state.id);
    }

}
