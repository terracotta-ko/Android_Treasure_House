package kobe.verticalviewpager_fragment_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kobe on 27/05/2017.
 */

public class FragmentPage extends Fragment {
    private int mLayoutId = 0;

    public FragmentPage() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //>> get layout's ID from bundle
        mLayoutId = getArguments().getInt("layoutID", R.layout.page_1);

        //>> return view
        return inflater.inflate(mLayoutId, container, false);
    }
}
