package com.hao.scientificresearch.utils;

import cn.hutool.core.util.RandomUtil;

public class GenNumber {

    public static String genNum(String str){
        return str + RandomUtil.randomNumbers(6);

    }

    public static void main(String[] args) {
        String s = RandomUtil.randomString(6);
        System.out.println(RandomUtil.randomNumbers(6));
        System.out.println(s);
//        System.out.println(GenNumber.genNum("test"));
//        System.out.println(GenNumber.genNum("test"));
//        System.out.println(GenNumber.genNum("test"));
    }

}
