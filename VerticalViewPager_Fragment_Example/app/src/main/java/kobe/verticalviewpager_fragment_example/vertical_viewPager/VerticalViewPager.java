package kobe.verticalviewpager_fragment_example.vertical_viewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import kobe.verticalviewpager_fragment_example.page_transformer.DefaultVerticalPageTransformer;

/**
 * Created by kobe on 27/05/2017.
 */

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(Context context) {
        super(context);
        setPageTransformer(false, new DefaultVerticalPageTransformer());
    }

    //>> this constructor is necessary when VerticalViewPager is used in Layout.xml
    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new DefaultVerticalPageTransformer());
    }

    private MotionEvent swapTouchEvent(MotionEvent motionEvent) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (motionEvent.getY() / height) * width;
        float swappedY = (motionEvent.getX() / width) * height;

        motionEvent.setLocation(swappedX, swappedY);

        return motionEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = super.onInterceptTouchEvent(swapTouchEvent(ev));
        swapTouchEvent(ev);
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }
}
