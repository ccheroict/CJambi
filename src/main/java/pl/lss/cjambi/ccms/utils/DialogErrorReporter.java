/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import com.google.inject.Singleton;

/**
 *
 * @author ctran
 */
@Singleton
public class DialogErrorReporter implements ErrorReporter {

    public DialogErrorReporter() {
    }

    @Override
    public void error(String message) {
    }
}
