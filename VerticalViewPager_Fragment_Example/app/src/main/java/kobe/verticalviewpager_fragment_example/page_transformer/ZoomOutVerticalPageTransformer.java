package kobe.verticalviewpager_fragment_example.page_transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by kobe on 27/05/2017.
 */

public class ZoomOutVerticalPageTransformer implements ViewPager.PageTransformer {
    private final float MIN_SCALE = 0.9f;
    private final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) {
            //>> [-INF, 1)

            //>> this page is way off-screen to the left
            page.setAlpha(0);
        }
        else if (position <= 1) {
            //>> [-1, 1]

            //>> modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
            float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;

            if (position < 0) {
                page.setTranslationX(horizontalMargin - verticalMargin / 2);
            }
            else {
                page.setTranslationX(-horizontalMargin + verticalMargin / 2);
            }

            //>> scale the page down ( between MIN_SCALE and 1 )
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            //>> fade the page relative to its size
            page.setAlpha(MIN_ALPHA
                    + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
        else {
            //>> (1, INF]

            //>> this page is way off-screen to the right
            page.setAlpha(0);
        }

        page.setTranslationX((float) pageWidth * -position);
        page.setTranslationY(position * (float)pageHeight);
    }
}
