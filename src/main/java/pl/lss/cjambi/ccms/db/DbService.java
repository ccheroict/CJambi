/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.db;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;
import pl.lss.cjambi.ccms.bean.Catalog;
import pl.lss.cjambi.ccms.bean.DiscountType;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Order;
import pl.lss.cjambi.ccms.bean.OrderStatus;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.bean.Tax;
import pl.lss.cjambi.ccms.bean.Unit;
import pl.lss.cjambi.ccms.bean.User;

/**
 *
 * @author ctran
 */
public interface DbService {

    public Dao getDao(Class clazz) throws SQLException;

    public void createTablesIfNessecary();

    public User login(String username, String password);

    public List<User> getUser(String query);

    public List<Order> getOrder(Filter filter);

    public void createOrUpdateOrder(Order order);

    public List<Supplier> getSupplier(Filter filter);

    public void createOrUpdateSupplier(Supplier bean) throws SQLException;

    public long countSupplier(Filter filter);

    public List<Product> getProduct(Filter filter);

    public List<DiscountType> getDiscountType();

    public List<Tax> getTax();

    public List<Unit> getUnit();

    public void createOrUpdateProduct(Product bean);

    public List<Catalog> getCatalog();

    public long countProduct(Filter filter);

    public List<OrderStatus> getOrderStatus();

    public Supplier getSupplierByCode(String supplierCode);

    public Product getProductByCode(String productCode);

    public long countOrder(Filter filter);
}
