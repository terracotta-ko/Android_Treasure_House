package kobe.contentprovider_example;

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
        ContentValues values = new ContentValues();
        values.put(MyContract.COL_DATE, ((EditText) findViewById(R.id.et_date)).getText().toString().trim());
        values.put(MyContract.COL_INFO, ((EditText) findViewById(R.id.et_info)).getText().toString().trim());
        values.put(MyContract.COL_AMOUNT, Integer.valueOf(((EditText) findViewById(R.id.et_amount)).getText().toString().trim()));

        getContentResolver().insert(MyContract.CONTENT_URI, values);
        finish();
    }

    public void onCancelBtnClick(View view) {
        finish();
    }
}
