package kobe.sqliteopenhelper_async_example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import static kobe.sqliteopenhelper_async_example.AddItemService.BROADCAST_UPDATED;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KKD";
    private MySQLiteOpenHelper mMySQLiteOpenHelper = null;
    private SimpleCursorAdapter mSimpleCursorAdapter = null;

    //>>update UI when data is updated
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mSimpleCursorAdapter.swapCursor(mMySQLiteOpenHelper.getReadableDatabase().query(
                    MyContract.TABLE_NAME, null, null, null, null, null, null
            ));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMySQLiteOpenHelper = MySQLiteOpenHelper.getInstance(this);
        setupListView();
    }

    private void setupListView() {
        Cursor cursor = mMySQLiteOpenHelper.getReadableDatabase().query(
                MyContract.TABLE_NAME, null, null, null, null, null, null);

        mSimpleCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.layout_item,
                cursor,
                new String[] {MyContract.COL_DATE, MyContract.COL_INFO, MyContract.COL_AMOUNT},
                new int[] {R.id.tv_date, R.id.tv_info, R.id.tv_amount},
                1
        );

        ((ListView) findViewById(R.id.list_view)).setAdapter(mSimpleCursorAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //>> register BroadcastReceiver to receive BROADCAST_UPDATED message
        registerReceiver(mBroadcastReceiver, new IntentFilter(BROADCAST_UPDATED));
    }

    @Override
    protected void onStop() {
        //>> unregister BroadcastReceiver
        unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mMySQLiteOpenHelper != null) {
            mMySQLiteOpenHelper.release();
        }

        mSimpleCursorAdapter = null;

        //>> release
        mBroadcastReceiver = null;

        super.onDestroy();
    }

    public void onAddBtnClick(View view) {
        startActivity(new Intent(this, AddActivity.class));
    }
}
