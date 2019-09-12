package kobe.recyclerview_swipe_menu;

import android.graphics.Canvas;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;
    private SwipeController mSwipeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configRecyclerView();
    }

    private void configRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);

        //>> config LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //>> config RecyclerView Adapter
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mMyRecyclerViewAdapter);

        //>> config scroll mode
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //>> attach ItemTouchHelper to RecyclerView Adapter
        mSwipeController = new SwipeController(mMyRecyclerViewAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSwipeController);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        //>> add DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        //>> add custom ItemDecoration
        //>> we draw delete button to each item by this method
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                mSwipeController.onDraw(c);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mSwipeController != null) {
            mSwipeController.release();
            mSwipeController = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.removeAllViews();
            mRecyclerView.setLayoutManager(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mMyRecyclerViewAdapter != null) {
            mMyRecyclerViewAdapter.release();
            mMyRecyclerViewAdapter = null;
        }
        super.onDestroy();
    }
}
