/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

import pl.lss.ccms.cjambi.bean.Supplier;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;

/**
 *
 * @author ctran
 */
public class SupplierToCodeConverter implements Converter<Supplier, String> {

    private static final DbService db = DbServiceImpl.getInstance();

    @Override
    public Supplier toData(String supplierCode) throws Exception {
        return db.getSupplierByCode(supplierCode);
    }

    @Override
    public String toPresentation(Supplier supplier) throws Exception {
        return (supplier == null ? null : supplier.code);
    }
}
