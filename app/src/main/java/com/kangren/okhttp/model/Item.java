package com.kangren.okhttp.model;

import java.io.Serializable;

import com.kangren.okhttp.model.Info;

/**
 * Created by kangren on 2017/11/8.
 */

public class Item implements Serializable{
    public static int i = 0;
    public int j;
    public Test test;
    public Info info;

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
