package kobe.verticalviewpager_fragment_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kobe.verticalviewpager_fragment_example.page_transformer.DepthVerticalPageTransformer;
import kobe.verticalviewpager_fragment_example.vertical_viewPager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vertical_viewpager);

        //>> use customize FragmentPagerAdapter
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        verticalViewPager.setAdapter(myFragmentPagerAdapter);

        //>> turn off over-scroll mode
        verticalViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //>> use some page transformer
        //verticalViewPager.setPageTransformer(false, new ZoomOutVerticalPageTransformer());
        verticalViewPager.setPageTransformer(false, new DepthVerticalPageTransformer());
    }
}
