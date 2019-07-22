package kobe.viewpager_fragment_example;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import kobe.viewpager_fragment_example.page_transformer.ZoomOutPageTransformer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager);

        //>> use customize FragmentPagerAdapter
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myFragmentPagerAdapter);

        //>> turn off over-scroll mode
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //>> use some page transformer
        viewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        //viewPager.setPageTransformer(false, new DepthPageTransformer());
    }
}
