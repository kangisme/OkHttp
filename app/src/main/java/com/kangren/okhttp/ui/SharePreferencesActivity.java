package com.kangren.okhttp.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
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
        People people = new People("kang", "男", 22);
        final Gson gson = new Gson();
        String peopleJson = gson.toJson(people);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("people", peopleJson);
        editor.apply();
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
