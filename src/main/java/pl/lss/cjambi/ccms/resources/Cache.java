/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.resources;

import com.google.inject.Guice;
import com.google.inject.Injector;
import pl.lss.cjambi.ccms.bean.User;
import pl.lss.cjambi.ccms.controller.AppInjector;

/**
 *
 * @author ctran
 */
public class Cache {

    public static final Injector injector = Guice.createInjector(new AppInjector());
    private static User user;

    public static <T> T getInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    public static void setUser(User loggedUser) {
        user = loggedUser;
    }

}
