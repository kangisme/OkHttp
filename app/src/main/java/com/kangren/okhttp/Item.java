package com.kangren.okhttp;

import java.io.Serializable;

/**
 * Created by kangren on 2017/11/8.
 */

public class Item implements Serializable{
    public static int i = 0;
    public int j;
    public Test test;
    public  Info info;

    public Item(int j) {
        this.j = j;
        test = new Test();
        test.i = j;
        info = new Info();
        info.anInt = j;
    }

    private static class Test
    {
        int i;
    }
}
