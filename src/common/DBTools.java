package common;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by mengxiangli on 2018/6/24.
 */
public class DBTools {

    private static Log logger = LogFactory.getLog(DBTools.class);
    public static Connection getConnection() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        try {
            Properties properties = new Properties();
            properties.load(DBTools.class.getClassLoader().getResourceAsStream("database.properties"));
            ds.setUser(properties.getProperty("username"));
            ds.setPassword(properties.getProperty("password"));
            ds.setDriverClass(properties.getProperty("driverClassName"));
            ds.setJdbcUrl(properties.getProperty("url"));
            return ds.getConnection();
        } catch (Exception ex) {
            logger.error("获取连接失败:" + ex.toString());
            throw new RuntimeException("获取连接失败!");
        }
    }
}
