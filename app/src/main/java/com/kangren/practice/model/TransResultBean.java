package com.kangren.practice.model;

/**
 * Created by kangren on 2017/12/11.
 */

public class TransResultBean {
    private String src;
    private String dst;

    public TransResultBean(String src, String dst) {
        super();
        this.src = src;
        this.dst = dst;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

}
