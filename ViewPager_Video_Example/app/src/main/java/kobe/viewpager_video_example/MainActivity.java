package kobe.viewpager_video_example;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ViewPagerVideoAdapter mViewPagerVideoAdapter;
    private String KEY_PAGE_IDX;
    private String KET_START_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KEY_PAGE_IDX = "currIdx";
        KET_START_TIME = "startTime";

        //>> set up ViewPager
        setupViewPager(savedInstanceState);
    }

    private void setupViewPager(Bundle savedInstanceState) {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //>> use customized adapter
        mViewPagerVideoAdapter = new ViewPagerVideoAdapter(this);

        //>> get data from savedInstanceState if there is any
        int firstPageIdx = 0;
        int startTimeMs = -1;
        if (savedInstanceState != null) {
            firstPageIdx = savedInstanceState.getInt(KEY_PAGE_IDX);
            startTimeMs = savedInstanceState.getInt(KET_START_TIME);
        }

        //>> setup first page
        mViewPagerVideoAdapter.setFirstPage(firstPageIdx, startTimeMs);

        mViewPager.setAdapter(mViewPagerVideoAdapter);

        //>> ViewPagerVideoAdapter also implements ViewPager.OnPageChangeListener
        mViewPager.addOnPageChangeListener(mViewPagerVideoAdapter);

        //>> use customized transformer
        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mViewPagerVideoAdapter != null) {
            mViewPagerVideoAdapter.resume();
        }
    }

    @Override
    protected void onPause() {
        if (mViewPagerVideoAdapter != null) {
            mViewPagerVideoAdapter.pause();
        }

        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mViewPagerVideoAdapter != null) {
            outState.putInt(KEY_PAGE_IDX, mViewPagerVideoAdapter.getCurrPageIdx());
            outState.putInt(KET_START_TIME, mViewPagerVideoAdapter.getCurrVideoStartTime());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (mViewPagerVideoAdapter != null) {
            mViewPagerVideoAdapter.release();
            mViewPagerVideoAdapter = null;
        }

        if (mViewPager != null) {
            mViewPager.removeAllViews();
            mViewPager.clearOnPageChangeListeners();
            mViewPager.setAdapter(null);
            mViewPager = null;
        }

        super.onDestroy();
    }
}
