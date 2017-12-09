package com.kangren.okhttp.model;

import com.google.gson.annotations.Expose;

/**
 * 使用注解自定义哪些字段需要序列化或反序列化
 * 没有@Expose注解的不会被序列化，serialize为false代表不序列化， deserialize则代表反序列化
 * Created by kangren on 2017/12/8.
 */

public class GsonBuilderBeanOne {

    @Expose
    private String username;

    @Expose(serialize = false, deserialize = true)
    private String password;

    @Expose(serialize = true, deserialize = false)
    private String school;

    private String classroom;

    @Expose(serialize = false)
    private String sex;

    public GsonBuilderBeanOne(String username, String password, String school,
                              String classroom, String sex) {
        super();
        this.username = username;
        this.password = password;
        this.school = school;
        this.classroom = classroom;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSchool() {
        return school;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getSex() {
        return sex;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {

        String resultString = "";
        resultString += "username:" + username + "\npassword:" + password
                + "\nschool:" + school + "\nclassroom:" + classroom + "\nsex:"
                + sex + "\n";

        return resultString;
    }

}
