package com.onlyas.app.utils.generator;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.JDBCConnectionFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

/**
 * Mybatis-Generator代码生成
 */
public class GeneratorStartUp {
    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        InputStream resourceAsStream = GeneratorStartUp.class.getClassLoader().getResourceAsStream("generator/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resourceAsStream);
        //新增处理MSSQL
        List<Context> llc = config.getContexts();
        Map<String, Object> dbColumnRemarkMap = new HashMap<>();
        Map<String, Object> dbTableRemarkMap = new HashMap<>();
        for (Context context : llc) {
            //JDBC
            JDBCConnectionConfiguration jdbcConnectionConfiguration = context.getJdbcConnectionConfiguration();
            //判断是否是MSSQL数据库
            String driver = jdbcConnectionConfiguration.getDriverClass();
            JDBCConnectionFactory jdbcConnectionFactory = new JDBCConnectionFactory(jdbcConnectionConfiguration);
            if (driver.equals("com.microsoft.sqlserver.jdbc.SQLServerDriver")) {
                /*
                String url = jdbcConnectionConfiguration.getConnectionURL();
                String user = jdbcConnectionConfiguration.getUserId();
                String pwd = jdbcConnectionConfiguration.getPassword();
                Connection conn = getConnection(driver, url, user, pwd);
                if (null == conn) {
                    System.out.println("数据库连接失败-" + url);
                    continue;
                }
                */
                Connection conn = jdbcConnectionFactory.getConnection();
                QueryRunner qr = new QueryRunner();
                //Tables
                List<TableConfiguration> tableConfigurations = context.getTableConfigurations();
                Map<String, Object> tableColumnMap = new HashMap<>();
                Map<String, Object> tableRemarkMap = new HashMap<>();
                for (TableConfiguration table : tableConfigurations) {
                    List<ColRemark> tableRemark = new ArrayList<>();
                    String sql_table = "SELECT objname,value FROM  ::fn_listextendedproperty(NULL, 'user', 'dbo', 'table', ?, default, default)";
                    try {
                        tableRemark = qr.query(conn, sql_table, new BeanListHandler<>(ColRemark.class), table.getTableName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (tableRemark.size() > 0) {
                        ColRemark remark = tableRemark.get(0);
                        tableRemarkMap.put(remark.getObjname(), remark.getValue());
                    }

                    List<ColRemark> list = new ArrayList<>();
                    String sql = "SELECT objname,value FROM  ::fn_listextendedproperty(NULL, 'user', 'dbo', 'table', ?, 'column', default)";
                    try {
                        list = qr.query(conn, sql, new BeanListHandler<>(ColRemark.class), table.getTableName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Map<String, Object> colMap = new HashMap<>();
                    for (ColRemark col : list) {
                        colMap.put(col.getObjname(), col.getValue());
                        //System.out.println(col.toString());
                    }

                    tableColumnMap.put(table.getTableName(), colMap);
                }
                dbColumnRemarkMap.put(context.getId(), tableColumnMap);
                dbTableRemarkMap.put(context.getId(), tableRemarkMap);
                conn.close();
            }
        }
        MBGlobal.dbColumnRemarkMap = dbColumnRemarkMap;
        MBGlobal.dbTableRemarkMap = dbTableRemarkMap;

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        for (String warning : warnings) {
            System.out.println(warning);
        }
    }

    private static Connection getConnection(String driver, String url, String user, String password) {
        Connection conn = null;
        try {
            Class.forName(driver);
            //conn = DriverManager.getConnection(url, user, password);
            Properties props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            //#mysql
            //props.setProperty("remarks", "true"); //设置可以获取remarks信息
            //props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
            //#oracle
            //props.setProperty("remarks", "true");
            //props.setProperty("remarksReporting", "true");
            conn = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
