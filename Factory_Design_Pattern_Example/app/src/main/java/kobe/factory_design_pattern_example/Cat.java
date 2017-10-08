package kobe.factory_design_pattern_example;

/**
 * Created by kobe on 09/10/2017.
 */

public class Cat implements Animal {
    @Override
    public String showMsg() {
        return "Cat is running";
    }

    public String eatFish() {
        return "Cat eats fish";
    }
}
