package kobe.contentprovider_example;

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

import static kobe.contentprovider_example.AddItemService.BROADCAST_UPDATED;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter mSimpleCursorAdapter = null;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mSimpleCursorAdapter != null) {
                mSimpleCursorAdapter.changeCursor(getContentResolver().query(
                        MyContract.CONTENT_URI,
                        null, null, null, null
                ));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mBroadcastReceiver, new IntentFilter(BROADCAST_UPDATED));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mSimpleCursorAdapter = null;
        mBroadcastReceiver = null;

        super.onDestroy();
    }

    private void setupListView() {
        Cursor cursor = getContentResolver().query(
                MyContract.CONTENT_URI,
                null, null, null, null
        );

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

    public void onAddBtnClick(View view) {
        startActivity(new Intent(this, AddActivity.class));
    }
}
