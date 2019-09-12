package com.intowow.read_raw_data_example;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ItemData> mItemData = new ArrayList<>();
    private String KEY_NAME   = "name";
    private String KEY_AGE    = "age";
    private String KEY_GENDER = "gender";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readJsonDataFromRawResource();

        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        for (ItemData d : mItemData) {
            HashMap<String,String> map = new HashMap<>();
            map.put(KEY_NAME, d.getName());
            map.put(KEY_AGE, String.valueOf(d.getAge()));
            map.put(KEY_GENDER, d.getGender());
            list.add(map);
        }

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(new SimpleAdapter(
                this,
                list,
                R.layout.layout_item,
                new String[] {KEY_NAME, KEY_AGE, KEY_GENDER},
                new int[] {R.id.tv_name, R.id.tv_age, R.id.tv_gender}
        ));
    }

    private void readJsonDataFromRawResource() {

        //>> user Context.getResources() to access JSON data
        InputStream inputStream = getResources().openRawResource(R.raw.data);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        try {
            //>> load JSON data
            String line = bufferedReader.readLine();
            while(line != null) {
                sb.append(line);
                line = bufferedReader.readLine();
            }

            //>> parse JSON
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.optJSONArray("users");

            if(jsonArray != null) {
                final int len = jsonArray.length();
                JSONObject item;

                for(int i = 0; i < len; i++) {
                    item = jsonArray.optJSONObject(i);
                    mItemData.add(new ItemData(
                            item.optString("name"),
                            item.optInt("age"),
                            item.optString("gender")));
                }
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
