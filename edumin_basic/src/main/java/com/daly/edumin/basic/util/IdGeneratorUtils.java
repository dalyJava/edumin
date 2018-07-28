package com.daly.edumin.basic.util;

import com.daly.edumin.basic.common.RandomBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daly on 2018/7/4.
 */
public class IdGeneratorUtils {
    private static final String SYS_USER_CLASS = "SysUser";
    private static final String SYS_FILE_CLASS = "SysFile";
    private static final String SYS_MENU_CLASS = "SysMenu";
    private static Logger LOG = org.slf4j.LoggerFactory.getLogger(IdGeneratorUtils.class);


    /**
     * 生成用户ID
     * @return
     */
    public static Integer idGeneratorOfSysUser(){
        String defaultPart1 = ConvertToASCII(SYS_USER_CLASS);
        String defaultPart2 = idGenerator();
        return Integer.parseInt(defaultPart1+defaultPart2);
    }


    /**
     * 生成ID的公共部分代码
     * @return
     */
    public static String idGenerator(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String defaultPart2 = sdf.format(new Date());
        String defaultPart3 = RandomBuilder.getRandomOfTwoDigit().toString();
        return defaultPart2+defaultPart3;
    }
    /**
     * 生成文件ID
     * @return
     */
    public static Integer idGeneratorOfSysFile(){
        String defaultPart1 = ConvertToASCII(SYS_FILE_CLASS);
        String defaultPart2 = idGenerator();
        return Integer.parseInt(defaultPart1+defaultPart2);
    }
    /**
     * 生成菜单ID
     * @param parentId MaxId
     * @return
     */
    public static Integer idGeneratorOfSysMenu(String  clazzName,String parentId,String maxId){
        if(!StringUtils.equals(clazzName,SYS_MENU_CLASS))
            return null;
        if(StringUtils.isBlank(parentId))
            parentId = "100";
        if(StringUtils.isBlank(maxId))
            maxId = "100";
        return Integer.parseInt(parentId+maxId);
    }

    /**
     * 将字符串转换成ASCII码
     * @param str
     */
    public static String ConvertToASCII(String str)
    {
        StringBuilder sb = new StringBuilder();
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            sb.append(Integer.valueOf(ch[i]).intValue());
        }
        return sb.toString();
    }

}
