package kobe.cursorloader_example;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "KKD";
    private SimpleCursorAdapter mSimpleCursorAdapter = null;
    static public final int LOADER_ID = 87;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupListView();

        //>> init Loader and then we implement the callbacks to query data through CursorLoader
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    protected void onDestroy() {
        mSimpleCursorAdapter = null;

        super.onDestroy();
    }

    private void setupListView() {
        mSimpleCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.layout_item,
                null,
                new String[] {MyContract.COL_DATE, MyContract.COL_INFO, MyContract.COL_AMOUNT},
                new int[] {R.id.tv_date, R.id.tv_info, R.id.tv_amount},
                1
        );

        ((ListView) findViewById(R.id.list_view)).setAdapter(mSimpleCursorAdapter);
    }

    public void onAddBtnClick(View view) {
        startActivity(new Intent(this, AddActivity.class));
    }

    //>>>>>> implements LoaderManager.LoaderCallbacks<Cursor>

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: YO");
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(
                        this,
                        MyContract.CONTENT_URI,
                        null, null, null, null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mSimpleCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSimpleCursorAdapter.swapCursor(null);
    }

    //<<<<<<
}
