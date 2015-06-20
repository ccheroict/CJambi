/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.trolltech.qt.core.QTimer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import pl.lss.ccms.cjambi.bean.Catalog;
import pl.lss.ccms.cjambi.bean.Company;
import pl.lss.ccms.cjambi.bean.DiscountType;
import pl.lss.ccms.cjambi.bean.Item;
import pl.lss.ccms.cjambi.bean.Office;
import pl.lss.ccms.cjambi.bean.Order;
import pl.lss.ccms.cjambi.bean.OrderStatus;
import pl.lss.ccms.cjambi.bean.Product;
import pl.lss.ccms.cjambi.bean.Supplier;
import pl.lss.ccms.cjambi.bean.Tax;
import pl.lss.ccms.cjambi.bean.Unit;
import pl.lss.ccms.cjambi.bean.User;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.resources.Cache;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.DialogErrorReporter;
import pl.lss.cjambi.ccms.utils.ErrorReporter;
import pl.lss.cjambi.ccms.utils.Utils;

/**
 *
 * @author ctran
 */
public class DbServiceImpl implements DbService {

    public static String JDBC_DEV = "jdbc:mysql://sql.ciibjeans.home.pl:3306/09182262_ccms?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
    public static String USERNAME_DEV = "09182262_ccms";
    public static String PASSWORD_DEV = "ChungCC2015";
//    public static String JDBC_DEV = "jdbc:mysql://localhost:3306/ccms?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
//    public static String USERNAME_DEV = "root";
//    public static String PASSWORD_DEV = "";

    private static final int SESSION_LENGTH = 10 * 60 * 1000;
    private static final String ISACTIVE = "isActive";
    private static final Logger logger = Logger.getLogger(DbServiceImpl.class);
    private static final String dbConfigFilePath = System.getProperty("user.dir") + File.separator + "db.json";
    private static final ErrorReporter reporter = DialogErrorReporter.getInstance();
    private static final QTimer timer = new QTimer();
    private JdbcPooledConnectionSource connectionSource;
    private static final DbService instance = new DbServiceImpl();

    public static DbService getInstance() {
        return instance;
    }

    private DbServiceImpl() {
        try {
            connectionSource = new JdbcPooledConnectionSource(JDBC_DEV, USERNAME_DEV, PASSWORD_DEV);
//            connectionSource = new JdbcPooledConnectionSource(obj.get("jdbcUrl").getAsString(), obj.get("username").getAsString(), obj.get("password").getAsString());
            connectionSource.setTestBeforeGet(true);
            timer.setInterval(SESSION_LENGTH);
            timer.timeout.connect(this, "keepSessionAlive()");
        } catch (SQLException ex) {
            reporter.error(I18n.errorWhileConnectingToDatabase);
            throw new RuntimeException();
        }
    }

    private void keepSessionAlive() {
        try {
            //query to keep connection is not closed
            Dao dao = getDao(Tax.class);
            dao.queryForAll();
        } catch (SQLException ex) {
        }
    }

    private String readFile(String filePath) throws IOException {
        String result = "";
        try {
            File file = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (IOException ex) {
            throw ex;
        }
        return result;
    }

    @Override
    public void createTablesIfNessecary() {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Catalog.class);
            TableUtils.createTableIfNotExists(connectionSource, Company.class);
            TableUtils.createTableIfNotExists(connectionSource, Unit.class);
            TableUtils.createTableIfNotExists(connectionSource, Tax.class);
            TableUtils.createTableIfNotExists(connectionSource, DiscountType.class);
            TableUtils.createTableIfNotExists(connectionSource, Office.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Supplier.class);
            TableUtils.createTableIfNotExists(connectionSource, Product.class);
            TableUtils.createTableIfNotExists(connectionSource, Order.class);
            TableUtils.createTableIfNotExists(connectionSource, Item.class);
            TableUtils.createTableIfNotExists(connectionSource, OrderStatus.class);
        } catch (SQLException ex) {
            logger.error("createTablesIfNessecary", ex);
            reporter.error(I18n.errorWhileCreatingDatabase);
            throw new RuntimeException();
        }
    }

    @Override
    public Dao getDao(Class clazz) throws SQLException {
        return DaoManager.createDao(connectionSource, clazz);
    }

    @Override
    public User login(String username, String password) {
        try {
            Dao<User, Integer> dao = getDao(User.class);
            //TO-DO: binary check login
            return (User) dao.queryForFirst(dao.queryBuilder().where()
                    .like(User.USERNAME_FIELD, username).and()
                    .like(User.PASSWORD_FIELD, password).and()
                    .eq(ISACTIVE, 1).prepare());
        } catch (SQLException ex) {
            logger.error("login", ex);
            return null;
        }
    }

    @Override
    public List<User> getUser(String query) {
        try {
            Dao<User, Integer> dao = getDao(User.class);
            return dao.query(dao.queryBuilder().where().like(User.USERNAME_FIELD, "%" + query + "%").and().eq(ISACTIVE, 1).prepare());
        } catch (SQLException ex) {
            logger.error("getUser", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Order> getOrder(Filter filter) {
        try {
            Dao<Order, Integer> dao = getDao(Order.class);
            QueryBuilder qb = dao.queryBuilder();
            setOffsetAndLimit(qb, filter);
            qb.where().between(Order.CREATED_DATE_FIELD, filter.dateFrom, filter.dateTo).and().like(Order.CODE_FIELD, "%" + filter.orderCode + "%").and().eq(ISACTIVE, 1);
            List<Order> res = dao.query(qb.prepare());
            if (res == null) {
                res = new ArrayList<>();
            }
            return res;
        } catch (SQLException ex) {
            logger.error("getOrder", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public void createOrUpdateOrder(Order order) {
        try {
            Dao orderDao = getDao(Order.class);
            Dao itemDao = getDao(Item.class);
            order.lastChangedDate = new Date();
            if (order.id == null) { //new order
                Filter filter = new Filter();
                order.code = Cache.getUserInitial() + "/" + countOrder(filter);
            }
            orderDao.createOrUpdate(order);
            Iterator<Item> it = order.items.iterator();
            while (it.hasNext()) {
                Item item = it.next();
                item.order = order;
                itemDao.createOrUpdate(item);
            }
        } catch (SQLException ex) {
            logger.error("createOrUpdateOrder", ex);
        }
    }

    @Override
    public List<Supplier> getSupplier(Filter filter) {
        try {
            Dao dao = getDao(Supplier.class);
            QueryBuilder qb = dao.queryBuilder();
            qb.orderBy(Supplier.CODE_FIELD, true);
            setOffsetAndLimit(qb, filter);
            PreparedQuery query = qb.where().like(Supplier.CODE_FIELD, "%" + filter.supplierCode + "%").and().eq(ISACTIVE, 1).prepare();
            List<Supplier> res = dao.query(query);
            if (res == null) {
                res = new ArrayList<>();
            }
            return res;
        } catch (SQLException ex) {
            logger.error("getSupplier", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public void createOrUpdateSupplier(Supplier supplier) {
        try {
            Dao dao = getDao(Supplier.class);
            dao.createOrUpdate(supplier);
        } catch (SQLException ex) {
            logger.error("createTablesIfNessecary", ex);
        }
    }

    @Override
    public long countSupplier(Filter filter) {
        try {
            Dao dao = getDao(Supplier.class);
            QueryBuilder qb = dao.queryBuilder().setCountOf(true);
            PreparedQuery query = qb.where().like(Supplier.CODE_FIELD, "%" + filter.supplierCode + "%").and().eq(ISACTIVE, 1).prepare();
            return dao.countOf(query);
        } catch (SQLException ex) {
            logger.error("countSupplier", ex);
            return 0;
        }
    }

    @Override
    public List<Product> getProduct(Filter filter) {
        try {
            Dao dao = getDao(Product.class);
            QueryBuilder qb = dao.queryBuilder();
            qb.orderBy(Product.CODE_FIELD, true);
            setOffsetAndLimit(qb, filter);
            PreparedQuery query = qb.where().like(Product.CODE_FIELD, "%" + filter.productCode + "%").and().eq(ISACTIVE, 1).prepare();
            List<Product> res = dao.query(query);
            if (res == null) {
                res = new ArrayList<>();
            } else {
                for (Product product : res) {
                    product.finalPrice = Utils.getValueWithDiscount(product.originalPrice, product.discountValue, product.discountType);
                }
            }
            return res;
        } catch (SQLException ex) {
            logger.error("getProduct", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public List<DiscountType> getDiscountType() {
        try {
            Dao dao = getDao(DiscountType.class);
            return dao.queryForAll();
        } catch (SQLException ex) {
            logger.error("getDiscountType", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tax> getTax() {
        try {
            Dao dao = getDao(Tax.class);
            return dao.queryForAll();
        } catch (SQLException ex) {
            logger.error("getTax", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Unit> getUnit() {
        try {
            Dao dao = getDao(Unit.class);
            return dao.queryForAll();
        } catch (SQLException ex) {
            logger.error("getUnit", ex);
            return new ArrayList<>();
        }
    }

    private void setOffsetAndLimit(QueryBuilder qb, Filter filter) throws SQLException {
        if (filter.pageNum != null && filter.pageSize != null) {
            qb.offset(filter.pageNum * filter.pageSize).limit(filter.pageSize);
        }
    }

    @Override
    public void createOrUpdateProduct(Product bean) {
        try {
            Dao dao = getDao(Product.class);
            dao.createOrUpdate(bean);
        } catch (SQLException ex) {
            logger.error("createOrUpdateProduct", ex);
        }
    }

    @Override
    public List<Catalog> getCatalog() {
        try {
            Dao dao = getDao(Catalog.class);
            return dao.queryForAll();
        } catch (SQLException ex) {
            logger.error("getCatalog", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public long countProduct(Filter filter) {
        try {
            Dao dao = getDao(Product.class);
            QueryBuilder qb = dao.queryBuilder().setCountOf(true);
            PreparedQuery query = qb.where().like(Product.CODE_FIELD, "%" + filter.productCode + "%").and().eq(ISACTIVE, 1).prepare();
            return dao.countOf(query);
        } catch (SQLException ex) {
            logger.error("countProduct", ex);
            return 0;
        }
    }

    @Override
    public List<OrderStatus> getOrderStatus() {
        try {
            Dao dao = getDao(OrderStatus.class);
            return dao.queryForAll();
        } catch (SQLException ex) {
            logger.error("getOrderStatus", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public Supplier getSupplierByCode(String supplierCode) {
        if (supplierCode == null) {
            return null;
        }
        try {
            Dao dao = getDao(Supplier.class);
            QueryBuilder qb = dao.queryBuilder();
            List<Supplier> list = dao.query(qb.where().eq(Supplier.CODE_FIELD, supplierCode).and().eq(ISACTIVE, 1).prepare());
            if (list == null || list.size() != 1) {
                return null;
            }
            return list.get(0);
        } catch (SQLException ex) {
            logger.error("getSupplierByCode", ex);
            return null;
        }
    }

    @Override
    public Product getProductByCode(String productCode) {
        if (productCode == null) {
            return null;
        }
        try {
            Dao dao = getDao(Product.class);
            QueryBuilder qb = dao.queryBuilder();
            List<Product> list = dao.query(qb.where().eq(Supplier.CODE_FIELD, productCode).and().eq(ISACTIVE, 1).prepare());
            if (list == null || list.size() != 1) {
                return null;
            }
            return list.get(0);
        } catch (SQLException ex) {
            logger.error("getProductByCode", ex);
            return null;
        }
    }

    @Override
    public long countOrder(Filter filter) {
        try {
            Dao dao = getDao(Order.class);
            QueryBuilder qb = dao.queryBuilder();
            qb.setCountOf(true);
            qb.where().between(Order.CREATED_DATE_FIELD, filter.dateFrom, filter.dateTo).and().like(Order.CODE_FIELD, "%" + filter.orderCode + "%").and().eq(ISACTIVE, 1);
            return dao.countOf(qb.prepare());
        } catch (SQLException ex) {
            logger.error("countOrder", ex);
            return 0;
        }
    }
}
