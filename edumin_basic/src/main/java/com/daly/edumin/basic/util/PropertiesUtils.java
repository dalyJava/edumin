package com.daly.edumin.basic.util;

import com.daly.edumin.basic.common.PropertiesPlugIn;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by daly on 2018/6/15.
 */
public class PropertiesUtils {
    private static Logger Log = LoggerFactory.getLogger(PropertiesUtils.class);
    private static PropertiesPlugIn props;
    public static final String DEFUALT_MODULE_NAME = "edumin_basic";
    public static final String DEFUALT_PROPERTIES_FILE_NAME = "application.properties";




    /**
     * module 下的application.properties追加属性
     * @param moduleName
     */
    public static boolean applicationAppond(String moduleName, List<Map<String,String>> propList) throws IOException {
        loadProps(moduleName);
        for (Map<String,String> tempMap : propList){
            props.setProperty(tempMap);
        }
        try {
            String filePath = getPropertiesFilePath(moduleName);
            FileOutputStream fos = new FileOutputStream(filePath);
            // 将Properties集合保存到流中
            props.store(fos);
            fos.close();// 关闭流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * moduleName 可以传_null  默认为_edumin-basic
     * propertiesFileName 可以传_null  默认为_application.properties
     * @return
     * @throws IOException
     */
    public static String getPropertiesFilePath(String moduleName) throws IOException {
        return getPropertiesFilePath(moduleName,DEFUALT_PROPERTIES_FILE_NAME);
    }
    /**
     * moduleName 可以传_null  默认为_edumin-basic
     *propertiesFileName 可以传_null  默认为_application.properties
     * 获取项目 resource 资源文件夹目录+资源文件名
     * @param fileName
     * @return
     */
    public static String getPropertiesFilePath(String moduleName,String fileName) throws IOException {
        String filePath = PathUtils.getResourcePath(moduleName)+fileName;
        File file = new File(filePath);
        if(!file.exists()){
            Log.info(fileName+"-->资源文件不存在！");
            return null;
        }
        return filePath;
    }

    synchronized static private void loadProps(String moduleName){
        loadProps(moduleName,DEFUALT_PROPERTIES_FILE_NAME);
    }

    synchronized static private void loadProps(String moduleName,String fileName){
        props = new PropertiesPlugIn();
        InputStream in = null;
        try {
            String filePath = getPropertiesFilePath(moduleName,fileName);
            if(StringUtils.isBlank(filePath))
                return;
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
        } catch (FileNotFoundException e) {
            //logger.error("sysConfig.properties文件未找到");
        } catch (IOException e) {
            //logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                //logger.error("sysConfig.properties文件流关闭出现异常");
            }
        }
        //logger.info("properties文件内容：" + props);
    }
    public static Boolean updatePro(String key,String value) throws IOException {
        return updatePro( DEFUALT_MODULE_NAME,getPropertiesFilePath(DEFUALT_MODULE_NAME),key,value);
    }
    public static Boolean updatePro(String moduleName,String filePath,String key,String value){
        loadProps(moduleName);
        props.setProperty(key, value);
        // 文件输出流
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            // 将Properties集合保存到流中
            props.store(fos);
            fos.close();// 关闭流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从module里面 的properties里面拿值，如果值为null或"" 则在 basic module里面取值
     * @param moduleName
     * @param key
     * @return
     */
    public static String getProperty(String moduleName,String key){
        loadProps(moduleName);
        String value = props.getProperty(key);
        if (StringUtils.isBlank(value)){
            loadProps(DEFUALT_MODULE_NAME);
            value = props.getProperty(key);
        }
        return value;
    }

    public static String getProperty(String key){
        return getProperty(DEFUALT_MODULE_NAME,key);
    }
}
