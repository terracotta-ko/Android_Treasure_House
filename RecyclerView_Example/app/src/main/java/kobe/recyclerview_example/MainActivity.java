package kobe.recyclerview_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mMyRecyclerAdapter;
    private MyRecyclerAdapterV2 mMyRecyclerAdapterV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //>> we can optimize the layout flow of RecyclerView by calling
        //>> RecyclerView.setHasFixedSize(true)
        //>> because all items in this example have the same size
        mRecyclerView.setHasFixedSize(true);

        //>>>>>> to use RecyclerView, you have to specify an adapter and a layout manager

        //>> use built-in LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //>> use customize adapter
        mMyRecyclerAdapter = new MyRecyclerAdapter(this);
        mRecyclerView.setAdapter(mMyRecyclerAdapter);

//        mMyRecyclerAdapterV2 = new MyRecyclerAdapterV2(this);
//        mRecyclerView.setAdapter(mMyRecyclerAdapterV2);
    }

    @Override
    protected void onDestroy() {
        if (mRecyclerView != null) {
            mRecyclerView.removeAllViews();
            mRecyclerView.setLayoutManager(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mMyRecyclerAdapter != null) {
            mMyRecyclerAdapter.release();
            mMyRecyclerAdapter = null;
        }

        if (mMyRecyclerAdapterV2 != null) {
            mMyRecyclerAdapterV2.release();
            mMyRecyclerAdapterV2 = null;
        }

        super.onDestroy();
    }
}
