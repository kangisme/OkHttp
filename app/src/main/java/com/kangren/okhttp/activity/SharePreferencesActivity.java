package com.kangren.okhttp.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kangren.okhttp.R;
import com.kangren.okhttp.model.GsonBuilderBeanOne;
import com.kangren.okhttp.model.GsonBuilderBeanThree;
import com.kangren.okhttp.model.GsonBuilderBeanTwo;

/**
 * Created by kangren on 2017/11/29.
 */

public class SharePreferencesActivity extends Activity {
    private SharedPreferences sp;

    private Gson gson;

    private TextView result;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharedpreferences);
        init();
    }

    private void init() {
        sp = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        result = (TextView) findViewById(R.id.text_view);
        gson = new Gson();
        SingleAndList();
        complexObject();
        useAnnotation();
        deserializable();

        serializedName();

        versionGson();
    }

    /**
     * 版本解析，只解析1.0版本及以下的字段
     */
    private void versionGson() {
        findViewById(R.id.gson_since).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson2 = new GsonBuilder().setVersion(1.0).create();
                GsonBuilderBeanThree gsonBuilderBeanThree = new GsonBuilderBeanThree("144", "123");

                result.setText(gson2.toJson(gsonBuilderBeanThree));
            }
        });
    }

    /**
     * 自定义序列化名称
     */
    private void serializedName() {
        findViewById(R.id.serialized_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GsonBuilderBeanTwo gsonBuilderBeanTwo = new GsonBuilderBeanTwo("144", "123");
                result.setText(gson.toJson(gsonBuilderBeanTwo));
            }
        });
    }

    /**
     * 反序列化
     */
    private void deserializable() {
        findViewById(R.id.deserializable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson2 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

                String showString = "";
                String jsonString = "{'username':'144','password':'123','school':'华软','classroom':'软工五班','sex':'男'}";

                showString += "json:" + jsonString + "\n解析后的数据：\n";
                GsonBuilderBeanOne beanOne = gson2.fromJson(jsonString, GsonBuilderBeanOne.class);
                showString += beanOne.toString();

                showString += "----------------------\n";
                result.setText(showString);
            }
        });
    }

    /**
     * 只有GsonBuilder生成的Gson对象才能使注解生效
     */
    private void useAnnotation() {
        findViewById(R.id.use_annotation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson2 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                        .create();

                GsonBuilderBeanOne gsonBuilderBeanOne = new GsonBuilderBeanOne("144",
                        "123", "华软", "软工五班", "男");

                String resultJson = gson2.toJson(gsonBuilderBeanOne);
                result.setText(resultJson);
            }
        });
    }

    /**
     * 包含Object的Object
     */
    private void complexObject() {
        People people = new People("小明", "男", 25);
        Family family = new Family(people);
        family.setAddress("上海市");
        family.setTelephone("123456");
        SharedPreferences.Editor editor = sp.edit();
        String familyJson = gson.toJson(family);
        editor.putString("family", familyJson);
        editor.apply();
        findViewById(R.id.read_family).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String readJson = sp.getString("family", null);
                Family family1 = gson.fromJson(readJson, Family.class);
                result.setText(family1.toString());
            }
        });
    }

    /**
     * JSON存储读取Object和List<Object>
     */
    private void SingleAndList() {
        //单一对象
        People people = new People("kang", "男", 22);
        String peopleJson = gson.toJson(people);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("people", peopleJson);
        findViewById(R.id.read_json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String readJson = sp.getString("people", null);
                if (readJson != null)
                {
                    People readPeople = gson.fromJson(readJson, People.class);
                    result.setText(readPeople.toString());
                }
            }
        });
        //多个对象List
        List<People> peoples = new ArrayList<>();
        peoples.add(people);
        People temp = new People("ren", "男", 24);
        peoples.add(temp);
        String peoplesJson = gson.toJson(peoples);
        editor.putString("peoples", peoplesJson);
        editor.apply();
        findViewById(R.id.read_jsons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String readJson = sp.getString("peoples", null);
                if (readJson != null)
                {
                    List<People> readPeoples = gson.fromJson(readJson, new TypeToken<List<People>>() {}.getType());
                    String text = readPeoples.get(0).toString() + readPeoples.get(1).toString();
                    result.setText(text);
                }
            }
        });
    }

    private class Family{
        public String address;
        public String telephone;
        public People people;

        public Family(People people) {
            this.people = people;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public void setTelephone(String telephone)
        {
            this.telephone = telephone;
        }

        @Override
        public String toString() {
            return people.toString() + "address:" + address + "telephone:" + telephone;
        }
    }


    private class People{
        public String name;
        public String sex;
        public int age;

        public People(String name, String sex, int age) {
            this.name = name;
            this.sex = sex;
            this.age = age;
        }

        @Override
        public String toString() {
            return "name:" + name + "sex:" + sex + "age:" + age;
        }
    }
}
