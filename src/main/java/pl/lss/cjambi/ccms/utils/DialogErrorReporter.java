/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils;

import pl.lss.cjambi.ccms.view.dialog.InformationDialog;

/**
 *
 * @author ctran
 */
public class DialogErrorReporter implements ErrorReporter {

    private static final DialogErrorReporter instance = new DialogErrorReporter();

    public static ErrorReporter getInstance() {
        return instance;
    }

    @Override
    public void error(String message) {
        InformationDialog.getInstance()
                .setType(InformationDialog.InformationType.ERROR)
                .setMessage(message).exec();
    }
}
