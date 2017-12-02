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
import com.google.gson.reflect.TypeToken;
import com.kangren.okhttp.R;

/**
 * Created by kangren on 2017/11/29.
 */

public class SharePreferencesActivity extends Activity {
    private SharedPreferences sp;

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
        //单一对象
        People people = new People("kang", "男", 22);
        final Gson gson = new Gson();
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
                    String text = "姓名:" + readPeople.name + "\n性别:" + readPeople.sex + "\n年龄:" + readPeople.age;
                    result.setText(text);
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
                    String text = readPeoples.get(0).name + readPeoples.get(0).sex + readPeoples.get(0).age +
                            "\n" + readPeoples.get(1).name + readPeoples.get(1).sex + readPeoples.get(1).age;
                    result.setText(text);
                }
            }
        });
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
    }
}
