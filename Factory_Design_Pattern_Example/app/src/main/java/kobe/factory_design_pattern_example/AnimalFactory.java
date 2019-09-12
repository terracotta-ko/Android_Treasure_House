package kobe.factory_design_pattern_example;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by kobe on 09/10/2017.
 */

public class AnimalFactory {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DOG, CAT})
    public @interface Animals{

    }

    public static final int DOG = 0;
    public static final int CAT = 1;


    static Animal produce(@Animals int type) {
        switch (type) {
            case DOG: return new Dog();
            case CAT: return new Cat();
        }

        return null;
    }
}
