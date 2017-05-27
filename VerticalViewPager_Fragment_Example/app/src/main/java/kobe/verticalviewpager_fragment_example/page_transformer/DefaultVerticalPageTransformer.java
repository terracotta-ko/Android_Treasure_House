package kobe.verticalviewpager_fragment_example.page_transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by kobe on 27/05/2017.
 */

public class DefaultVerticalPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX((float)page.getWidth() * -position);
        page.setTranslationY(position * (float)page.getHeight());
    }
}
