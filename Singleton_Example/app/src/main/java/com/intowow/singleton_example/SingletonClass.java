package com.intowow.singleton_example;

/**
 * Created by kobe on 28/03/2018
 */

public class SingletonClass {
    private volatile static SingletonClass mInstance = null;

    static SingletonClass getInstance() {
        if (mInstance == null) {
            synchronized (SingletonClass.class) {
                if (mInstance == null) {
                    mInstance = new SingletonClass();
                }
            }
        }

        return mInstance;
    }
}
