package com.daly.edumin.basic.util;


import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

/**
 * Created by daly on 2018/6/14.
 */
public class DbUtils {
    private static Logger LOG = LoggerFactory.getLogger(DbUtils.class);
    private static final String DRIVER_CLASS_NAME = "mysql.driver-class-name";
    private static final String DynamicDBURL = "mysql.url";
    private static final String PASSWORD = "mysql.password";
    private static final String USERNAME = "mysql.username";
    private static final String MYSQL_BASIC_URL = "mysql.basicURL";
    /**
     * 根据module 下的sql文件 名 ，校验数据表格是否存在，不存在 就创建表格
     * 因为是更新文件，在初始化时，已经做过非空校验了
     * @param moduleName
     */
    public static void updateDBTable(String moduleName) throws Exception {
        List<String> tableNameList = getTableNameList(moduleName);
        int updateCounts = 0;
        boolean isUpdate = true;
        String sqlPath = PathUtils.getSQLFilesPath(moduleName);
        List<File> sqlFileList = getSQLFilesByDirectory(sqlPath);
        for (File tempFile : sqlFileList){
            String str =tempFile.getName().split("\\.")[0];
            isUpdate = !tableNameList.contains(tempFile.getName().split("\\.")[0]);
            if(isUpdate){
                execSqlFileByMysql(moduleName,tempFile);
                updateCounts ++;
            }
        }
        LOG.info("The total of updateTables in " + moduleName + " is " + updateCounts );
    }

    /**
     * 通过resources下的sql文件夹地址，获取sql文件夹下所有的.sql文件
     * @param sqlDirectory
     * @return
     */
    public static List<File> getSQLFilesByDirectory(String sqlDirectory){
        List<File> SQLFileList = new ArrayList<>();
        File file = new File(sqlDirectory);
        File[] sqlFliesArr = file.listFiles();
        if(sqlFliesArr.length==0){
            return null;
        }
        for(int i=0;i<sqlFliesArr.length;i++){
            if(sqlFliesArr[i].isFile()){
                SQLFileList.add(sqlFliesArr[i]);
            }
            if(sqlFliesArr[i].isDirectory()){
                String tempPath = sqlFliesArr[i]+"";
                List<File> tempSQLFileList =getSQLFilesByDirectory(tempPath);
                if(tempSQLFileList != null )
                    SQLFileList.addAll(tempSQLFileList);
            }
        }
        return SQLFileList;
    }

    /**
     * 获取module对应的数据库的数据库表 List
     * @param moduleName
     * @return
     */
    public static List<String> getTableNameList(String moduleName) throws Exception {
        List<String> tableNameList = new ArrayList<>();
        Exception error = null;
        Connection conn = null;
        try {
            String sql = "select table_name from information_schema.tables where table_schema='"+moduleName+"'";
            Map<String,String> dbParam = getDBParam(moduleName);
            Class.forName(dbParam.get("driver"));
            conn = DriverManager.getConnection(dbParam.get("url"), dbParam.get("userName"), dbParam.get("pwd"));
            Statement  statement = conn.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while(set.next()){
                tableNameList.add(set.getString("table_name"));
            }
        } catch (SQLException e) {
            LOG.info("The tables of modules are update failed ... ");
            e.printStackTrace();
        }finally{
            close(conn);
        }
        return tableNameList;
    }



    /**
     * 对系统的基础数据进行校验，初始化
     * 每个数据库表对应一个xml文件
     * 如果数据库表不存在在运行相关sql文件创建表格
     * 所有sql文件放在各自module下的resource文件夹下
     */
    public  static void DBInit(String moduleName) throws Exception {
        if(!DbUtils.isInitDB(moduleName))
            return;
        String sqlPath = PathUtils.getSQLFilesPath(moduleName);
        if (sqlPath == null)
            return;
        File file = new File(sqlPath);
        if(!file.isDirectory()){
            LOG.info(moduleName + "的sql文件夹不存在！");
            return;
        }
        File[] sqlFliesArr = file.listFiles();
        if(sqlFliesArr.length == 0){
            LOG.info(moduleName+"-->sql是一个空的文件夹！");
        }
        DbUtils.execSqlFileByMysql(moduleName,sqlFliesArr);
        //初始化玩数据库之后，将defualtInit的值改为false
        PropertiesUtils.updatePro(moduleName+".dbinit","false");
    }


    /**
     * 传入moduleName名称，判断是否需要初始化数据库
     * 初始化数据库 满足的条件
     * ① minedu-basic --> application.properties --> *.dbinit 的值为true
     * ② 数据库存在
     * ③ 数据库表存在时 不初始化表格，log提醒
     * @param moduleName
     * @return
     */
    public static boolean isInitDB(String moduleName) throws IOException {
        Boolean defualtDBinit = false;
        String strDBinit = PropertiesUtils.getProperty(moduleName+".dbinit");
        if(StringUtils.isNotBlank(strDBinit)){
            if(StringUtils.equalsIgnoreCase(strDBinit,"true"))
                defualtDBinit = Boolean.valueOf(strDBinit);
        }else{
            LOG.info("minedu-basic --> application.properties --> +"+moduleName+".dbinit of value is null or \"\" ");
        }
        return defualtDBinit;
    }

    /**
     * 执行sql文件数组
     * @param moduleName
     * @param sqlFileArr
     * @throws Exception
     */
    public static void execSqlFileByMysql(String moduleName ,File[] sqlFileArr) throws Exception{
        List<File> sqlFileList = Arrays.asList(sqlFileArr);
        execSqlFileByMysql(moduleName,sqlFileList);
    }

    /**
     * 执行单个sql文件
     * @param moduleName
     * @param sqlFile
     * @throws Exception
     */
    public static void execSqlFileByMysql(String moduleName ,File sqlFile) throws Exception{
        List<File> sqlFileList =Arrays.asList(sqlFile);
        execSqlFileByMysql(moduleName,sqlFileList);
    }

    /**
     * 获取JDBC连接的相关键值对
     * @param moduleName
     * @return
     */
    public static Map<String,String> getDBParam(String moduleName){
        String driver = PropertiesUtils.getProperty(moduleName,DRIVER_CLASS_NAME);
        String url = getDynamicDBURL(moduleName);
        String pwd = PropertiesUtils.getProperty(moduleName,PASSWORD);
        String userName = PropertiesUtils.getProperty(moduleName,USERNAME);
        Map<String,String> dbParamMap = new HashMap<>();
        dbParamMap.put("driver",driver);
        dbParamMap.put("url",url);
        dbParamMap.put("pwd",pwd);
        dbParamMap.put("userName",userName);
        return dbParamMap;
    }


    /**
     * 根据moduleName 获取相应的数据库连接URL的值
     * @param moduleName
     * @return
     */
    public static String getDynamicDBURL(String moduleName){
        String tempURL = PropertiesUtils.getProperty(DynamicDBURL);
        String DBURL = null;
        if(StringUtils.isNotBlank(tempURL)){
            DBURL = tempURL.replace("{moduleName}",moduleName);
        }
        return DBURL;
    }

    /**
     * 执行单个sql文件
     * @param moduleName
     * @param sqlFileList
     * @throws Exception
     */
    public static void execSqlFileByMysql(String moduleName ,List<File> sqlFileList) throws Exception{
        Exception error = null;
        Connection conn = null;
        try {
            Map<String,String> dbParam = getDBParam(moduleName);
            Class.forName(dbParam.get("driver"));
            conn = DriverManager.getConnection(dbParam.get("url"), dbParam.get("userName"), dbParam.get("pwd"));
            ScriptRunner runner = new ScriptRunner(conn);
            //下面配置不要随意更改，否则会出现各种问题
            runner.setAutoCommit(true);//自动提交
            runner.setFullLineDelimiter(false);
            runner.setDelimiter(";");////每条命令间的分隔符
            runner.setSendFullScript(false);
            runner.setStopOnError(false);
            //  runner.setLogWriter(null);//设置是否输出日志
            //如果又多个sql文件，可以写多个runner.runScript(xxx),
            for (File temp : sqlFileList) {
                runner.runScript(new InputStreamReader(new FileInputStream(temp),"utf-8"));
            }
        } catch (MySQLSyntaxErrorException mySQLSyntaxErrorException){
            LOG.error("需要初始化的数据库"+moduleName+"不存在");
            Map<String,String> dbParam = getDBParam(moduleName);
            dbParam.put("url",PropertiesUtils.getProperty(MYSQL_BASIC_URL));
            Class.forName(dbParam.get("driver"));
            conn = DriverManager.getConnection(dbParam.get("url"), dbParam.get("userName"), dbParam.get("pwd"));
            LOG.info("Creating database "+moduleName+"...");
            String sql = "create database `"+moduleName+"` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
            LOG.info("Database created successfully...");
            execSqlFileByMysql(moduleName,sqlFileList);
        }catch (Exception e) {
            LOG.error("执行sql文件进行数据库创建失败....",e);
            error = e;
        }finally{
            close(conn);
        }
        if(error != null){
            throw error;
        }
    }


    private static void close(Connection conn){
        try {
            if(conn != null){
                conn.close();
            }
        } catch (Exception e) {
            if(conn != null){
                conn = null;
            }
        }
    }




}
