/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.db;

import java.util.List;
import pl.lss.cjambi.ccms.bean.User;

/**
 *
 * @author ctran
 */
public interface DbService {

    public void createTablesIfNessecary();

    public User login(String username, String password);

    public List<User> getUser(String query);

}
