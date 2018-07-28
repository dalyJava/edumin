package com.daly.edumin.basic.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by daly on 2018/6/14.
 */
public class PathUtils {
    private static Logger LOG = LoggerFactory.getLogger(PathUtils.class);

    /**
     * 请求获取module下的资源文件sql
     * @param moduleName
     * @return
     * @throws IOException
     */
    public static String getSQLFilesPath(String moduleName) throws IOException {
        String sqlPath = null;
        String modulePath = getModulePathByName(moduleName);
        if(modulePath != null){
            sqlPath = modulePath+"src\\main\\resources\\sql";
        }
        return sqlPath;
    }

    /**
     * 请求获取module的路径
     * @param moduleName
     * @return
     * @throws IOException
     */
    public  static String getModulePathByName(String moduleName) throws IOException {
       String modulePath = currentPath()+moduleName+"\\";
        File file = new File(modulePath);
        if(!file.isDirectory()){
            modulePath = null;
            LOG.info("请求的module不存在");
        }
        return modulePath;
    }

    /**
     * 获取当期项目resource路径
     * @return
     */
    public static String getResourcePath(String moduleName) throws IOException {
        String resourcePath = currentPath()+moduleName+"\\src\\main\\resources\\";
        File file = new File(resourcePath);
        if(!file.isDirectory()){
            LOG.info(moduleName+"--> resources 文件夹不存在！");
            return null;
        }
        return resourcePath;
    }

    /**
     * 获取当前运行程序jar包所在路径
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getProjectPath() throws UnsupportedEncodingException {
        URL url = PathUtils.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");
        if(filePath.endsWith(".jar"))
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        return filePath;
    }
    /**
     * 返回当前classpath路径
     * @return
     * @throws IOException
     */
    public static String currentPath() throws IOException {
        File directory =   new   File(" ");
        //取得当前路径
        return directory.getCanonicalPath();

    }
    /**
     * 获取一个Class的绝对路径
     *
     * @param clazz
     * @return Class的绝对路径
     *
     */
    public static String getPathByClass(Class clazz) {
        String path = null;
        try {
            URI uri = clazz.getResource("").toURI();
            File file = new File(uri);
            path = file.getCanonicalPath();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return path;

    }

    /**
     * 获取一个文件相对于一个Class相对的绝对路径
     *
     * @param clazz
     *            Class对象
     * @param relativePath 追加路径参数
     *            Class对象的相对路径
     * @return 文件绝对路径
     */
    public static String getFilePathByClass(Class clazz, String relativePath) {
        String filePath = null;
        String clazzPath = getPathByClass(clazz);
        StringBuffer sbPath = new StringBuffer(clazzPath);
        sbPath.append(File.separator);
        sbPath.append(relativePath);
        File file = new File(sbPath.toString());
        try {
            filePath = file.getCanonicalPath();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return filePath;

    }
}
