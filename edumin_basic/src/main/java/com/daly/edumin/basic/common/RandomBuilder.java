package com.daly.edumin.basic.common;

import java.util.Random;

/**
 * 生成随机数
 * Created by daly on 2018/7/4.
 */
public class RandomBuilder {

    /**
     * 获取2位整数随机数
     * @return
     */
    public static Integer getRandomOfTwoDigit(){
        Random random = new Random();
        return  random.nextInt(90) + 10;
    }

}
