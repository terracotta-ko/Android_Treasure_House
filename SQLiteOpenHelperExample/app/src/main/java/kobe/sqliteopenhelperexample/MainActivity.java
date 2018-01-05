package kobe.sqliteopenhelperexample;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KKD";
    private static final int REQUEST_ADD = 87;

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private MySQLiteHelper mMySQLiteHelper = null;
    private SimpleCursorAdapter mSimpleCursorAdapter = null;
    private boolean mSortedBtAmount = false;
    private Button mSortBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMySQLiteHelper = MySQLiteHelper.getInstance(this);
        mSortBtn = (Button) findViewById(R.id.btn_sort);
        setupListView();
    }

    private void setupListView() {
        Cursor cursor = mMySQLiteHelper.getReadableDatabase().query(
                MyContract.TABLE_NAME, null, null, null, null, null, null);

        ListView listView = (ListView) findViewById(R.id.list_view);

        mSimpleCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.layout_item,
                cursor,
                new String[] {MyContract.COL_DATE, MyContract.COL_INFO, MyContract.COL_AMOUNT},
                new int[] {R.id.tv_date, R.id.tv_info, R.id.tv_amount},
                1
        );

        listView.setAdapter(mSimpleCursorAdapter);
    }

    @Override
    protected void onDestroy() {
        if (mMySQLiteHelper != null) {
            mMySQLiteHelper.release();
        }

        super.onDestroy();
    }

    public void addBtnClick(View view) {
        startActivityForResult(new Intent(this, AddActivity.class), REQUEST_ADD);
    }

    public void onSortBtnClick(View view) {
        mSortedBtAmount = !mSortedBtAmount;

        if (mSortedBtAmount) {
            mSortBtn.setText("no sort");
            mSimpleCursorAdapter.swapCursor(mMySQLiteHelper.getReadableDatabase().query(
                    MyContract.TABLE_NAME, null, null, null, null, null, MyContract.COL_AMOUNT)
            );
        }
        else {
            mSortBtn.setText("sorted by amount");
            mSimpleCursorAdapter.swapCursor(mMySQLiteHelper.getReadableDatabase().query(
                    MyContract.TABLE_NAME, null, null, null, null, null, null)
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            mSimpleCursorAdapter.swapCursor(mMySQLiteHelper.getReadableDatabase().query(
                    MyContract.TABLE_NAME, null, null, null, null, null, null));
        }
    }
}
