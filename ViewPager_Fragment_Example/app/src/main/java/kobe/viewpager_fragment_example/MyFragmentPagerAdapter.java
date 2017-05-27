package kobe.viewpager_fragment_example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kobe on 27/05/2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList = null;
    private int[] mLayoutIds = new int[] {
            R.layout.page_1,
            R.layout.page_2,
            R.layout.page_3
    };


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        mFragmentList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("layoutID", mLayoutIds[i]);
            mFragmentList.add(new FragmentPage());
            mFragmentList.get(i).setArguments(bundle);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
