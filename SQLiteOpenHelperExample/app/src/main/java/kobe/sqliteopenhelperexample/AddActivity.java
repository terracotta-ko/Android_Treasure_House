package kobe.sqliteopenhelperexample;

import android.content.ContentValues;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "KKD";
    private MySQLiteHelper mMySQLiteHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mMySQLiteHelper = MySQLiteHelper.getInstance(getApplicationContext());
    }

    public void onAddBtnClick(View view) {
        if (mMySQLiteHelper != null) {

            final String date = ((EditText) findViewById(R.id.add_et_date)).getText().toString().trim();
            final String info = ((EditText) findViewById(R.id.add_et_info)).getText().toString().trim();
            final int amount = Integer.parseInt(((EditText) findViewById(R.id.add_et_amount)).getText().toString().trim());

            ContentValues values = new ContentValues();
            values.put(MyContract.COL_DATE, date);
            values.put(MyContract.COL_INFO, info);
            values.put(MyContract.COL_AMOUNT, amount);

            long id = mMySQLiteHelper.getWritableDatabase().insert(
                    MyContract.TABLE_NAME,
                    null,
                    values
            );

            if (id == -1) {
                Log.e(TAG, "onAddBtnClick: insert failed");
            }

            setResult(RESULT_OK);
            finish();
        }
    }

    public void onCancelBtnClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
