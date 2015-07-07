/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.utils.converter;

import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.db.DbService;
import pl.lss.cjambi.ccms.db.DbServiceImpl;

/**
 *
 * @author ctran
 */
public abstract class ProductToCodeConverter implements Converter<Product, String> {

    private static final DbService db = DbServiceImpl.getInstance();

    @Override
    public Product toData(String productCode) throws Exception {
        return db.getProductByCodeAndSupplier(productCode, getSupplier());
    }

    @Override
    public String toPresentation(Product product) throws Exception {
        return (product == null ? null : product.code);
    }

    public abstract Supplier getSupplier();
}
