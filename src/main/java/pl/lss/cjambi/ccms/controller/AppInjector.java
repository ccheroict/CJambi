/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.controller;

import com.google.inject.AbstractModule;
import org.apache.log4j.Logger;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;
import pl.lss.cjambi.ccms.utils.DialogErrorReporter;
import pl.lss.cjambi.ccms.utils.ErrorReporter;
import pl.lss.cjambi.ccms.view.InformationDialog;

/**
 *
 * @author ctran
 */
public class AppInjector extends AbstractModule {

    private static final Logger logger = Logger.getLogger(AppInjector.class);

    @Override
    protected void configure() {
        bind(ErrorReporter.class).to(DialogErrorReporter.class);
        bind(DbService.class).to(DbServiceImpl.class);
        bind(InformationDialog.class);
        bind(MainEntryPoint.class);
    }

}
