package kobe.viewpager_fragment_example;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by kobe on 27/05/2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private int[] mLayoutIds = new int[] {
            R.layout.page_1,
            R.layout.page_2,
            R.layout.page_3
    };


    MyFragmentPagerAdapter(FragmentManager fm) {
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
