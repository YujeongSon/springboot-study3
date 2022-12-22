package com.zerobase.lms.util;

public class PageUtilTest {

    public static void main(String[] args) {
        PageUtil pageUtil = new PageUtil(151, 10, 3, "");
        String page = pageUtil.pager();

        System.out.println(page);
    }
}
