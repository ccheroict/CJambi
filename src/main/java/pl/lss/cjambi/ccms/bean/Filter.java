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

    public String supplierCode = "";
    public String productCode = "";
    public Long pageNum;
    public Long pageSize;
    public Date dateFrom = Utils.getStartOfDay(new Date());
    public Date dateTo = Utils.getEndOfDay(new Date());
}
