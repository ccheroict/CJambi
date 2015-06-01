/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lss.cjambi.ccms.db;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
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
import pl.lss.cjambi.ccms.bean.Company;
import pl.lss.cjambi.ccms.bean.DiscountType;
import pl.lss.cjambi.ccms.bean.Filter;
import pl.lss.cjambi.ccms.bean.Item;
import pl.lss.cjambi.ccms.bean.Office;
import pl.lss.cjambi.ccms.bean.Order;
import pl.lss.cjambi.ccms.bean.Product;
import pl.lss.cjambi.ccms.bean.Supplier;
import pl.lss.cjambi.ccms.bean.Tax;
import pl.lss.cjambi.ccms.bean.Unit;
import pl.lss.cjambi.ccms.bean.User;
import pl.lss.cjambi.ccms.resources.I18n;
import pl.lss.cjambi.ccms.utils.ErrorReporter;

/**
 *
 * @author ctran
 */
@Singleton
public class DbServiceImpl implements DbService {

    private static final Logger logger = Logger.getLogger(DbServiceImpl.class);
    private static final String dbConfigFilePath = "db.json";
    private ConnectionSource connectionSource;
    @Inject
    private ErrorReporter reporter;

    public DbServiceImpl() {
        try {
            JsonParser parser = new JsonParser();
            String configStr = readFile(dbConfigFilePath);
            JsonObject obj = parser.parse(configStr).getAsJsonObject();
            connectionSource = new JdbcConnectionSource(obj.get("jdbcUrl").getAsString(), obj.get("username").getAsString(), obj.get("password").getAsString());
        } catch (IOException ex) {
            reporter.error(I18n.readDataSourceConfigurationError);
        } catch (SQLException ex) {
            reporter.error(I18n.errorWhileConnectingToDatabase);
        }
    }

    private String readFile(String filePath) throws IOException {
        String result = "";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(filePath).getFile());
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
        } catch (SQLException ex) {
            logger.error("createTablesIfNessecary", ex);
            reporter.error(I18n.errorWhileCreatingDatabase);
        }
    }

    @Override
    public User login(String username, String password) {
        try {
            Dao<User, Integer> dao = DaoManager.createDao(connectionSource, User.class);
            return (User) dao.queryForFirst(dao.queryBuilder().where().eq(User.USERNAME_FIELD, username).and().eq(User.PASSWORD_FIELD, password).prepare());
        } catch (SQLException ex) {
            logger.error("login", ex);
            return null;
        }
    }

    @Override
    public List<User> getUser(String query) {
        try {
            Dao<User, Integer> dao = DaoManager.createDao(connectionSource, User.class);
            return dao.query(dao.queryBuilder().where().like(User.USERNAME_FIELD, "%" + query + "%").prepare());
        } catch (SQLException ex) {
            logger.error("getUser", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Order> getOrder(Filter filter) {
        try {
            Dao<Order, Integer> dao = DaoManager.createDao(connectionSource, Order.class);
            return dao.queryForAll();
        } catch (SQLException ex) {
            logger.error("getOrder", ex);
            return new ArrayList<>();
        }
    }

    @Override
    public void createOrUpdateOrder(Order order) {
        try {
            Dao<Order, Integer> dao = DaoManager.createDao(connectionSource, Order.class);
            order.lastChangedDate = new Date();
            Iterator<Item> it = order.items.iterator();
            Item first = it.next();
            Item item = new Item();
            item.product = first.product;
            item.nPackRequired = 1;
            item.order = order;
            item.price = 300;
            item.quantity = 10;
            item.total = 3000;
            order.items.remove(first);
            order.items.add(item);

            dao.createOrUpdate(order);
        } catch (SQLException ex) {
            logger.error("createOrUpdateOrder", ex);
        }
    }
}
