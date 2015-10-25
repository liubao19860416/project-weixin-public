package com.zxytech.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.zxytech.util.Constants;

/**
 * DBCP方式获取连接池信息
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
@SuppressWarnings("static-access")
public class DBCPUtil {
    
    private DBCPUtil() {
        super();
    }

    private static DataSource dataSource;
    
    static {
        try {
            //方式1,需要路径信息,得到一个输入流,必须添加绝对路径分隔符和文件后缀名,建议使用方式2
            InputStream in = DBCPUtil.class.getResourceAsStream("/jdbc.properties");

            //方式2,必须添加文件后缀名,绝对路径分隔符可有可无,推荐使用
            InputStream in2 = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("jdbc.properties");
            //方式3,必须不能添加绝对路径分隔符和文件后缀名
            PropertyResourceBundle rb = (PropertyResourceBundle) PropertyResourceBundle
                    .getBundle("jdbc",Locale.CHINA); 

            Properties props = new Properties();
            props.load(in);
            dataSource = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("!");
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(" ");
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

}
