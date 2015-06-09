/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.bean;

import java.util.Date;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class Filter {

    public static final String SUPPLIER_CODE_FIELD = "supplierCode";
    public static final String PRODUCT_CODE_FIELD = "productCode";
    public static final String ORDER_CODE_FIELD = "orderCode";
    public static final String DATE_FROM_FIELD = "dateFrom";
    public static final String ORDER_DATE_TO_FIELD = "dateTo";

    public String supplierCode = "";
    public String productCode = "";
    public String orderCode = "";
    public Long pageNum = 0L;
    public Long pageSize = 20L;
    public Date dateFrom = Utils.getStartOfDay(new Date());
    public Date dateTo = Utils.getEndOfDay(new Date());
}
