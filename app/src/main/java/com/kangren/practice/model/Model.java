package com.kangren.practice.model;

import android.graphics.Bitmap;

/**
 * Created by kangren on 2017/8/31.
 */

public class Model {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    private Bitmap pic;
}
