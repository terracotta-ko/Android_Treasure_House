package kobe.factory_design_pattern_example;

/**
 * Created by kobe on 09/10/2017.
 */

public class Dog implements Animal {
    @Override
    public String showMsg() {
        return "Dog is running";
    }

    public String eatBones() {
        return "Dog eats bones";
    }
}
