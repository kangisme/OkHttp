package com.kangren.okhttp.model;

import com.google.gson.annotations.Since;

/**
 * 使用注解表示哪些字段是哪个版本的，@Since(1.0)代表1.0版本，应用版本比它高或同等时会被序列化，反之不会，也可以用@Until(1.0)
 * Created by kangren on 2017/12/8.
 */

public class GsonBuilderBeanThree {
    @Since(1.0)
    private String username;
    @Since(1.1)
    private String password;

    public GsonBuilderBeanThree(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {

        String resultString = "";
        resultString += "username:" + username + "\npassword:" + password + "\n";

        return resultString;
    }
}
