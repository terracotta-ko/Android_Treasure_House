package com.intowow.read_raw_data_example;

/**
 * Created by kobe on 21/03/2018
 */

public class ItemData {
    private String mName;
    private int mAge;
    private String mGender;

    public ItemData(final String name, final int age, final String gender) {
        mName = name;
        mAge  = age;
        mGender = gender;
    }

    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }

    public String getGender() {
        return mGender;
    }
}
