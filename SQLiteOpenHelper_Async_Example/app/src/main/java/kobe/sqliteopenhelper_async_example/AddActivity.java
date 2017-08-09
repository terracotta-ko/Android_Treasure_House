package kobe.sqliteopenhelper_async_example;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void onConfirmBtnClick(View view) {
        final String date = ((EditText) findViewById(R.id.et_date)).getText().toString().trim();
        final String info = ((EditText) findViewById(R.id.et_info)).getText().toString().trim();
        final int amount = Integer.valueOf(((EditText) findViewById(R.id.et_amount)).getText().toString().trim());

        ContentValues values = new ContentValues();
        values.put(MyContract.COL_DATE, date);
        values.put(MyContract.COL_INFO, info);
        values.put(MyContract.COL_AMOUNT, amount);

        AddItemService.addItem(this, values, true);
        finish();
    }

    public void onCancelBtnClick(View view) {
        finish();
    }
}
