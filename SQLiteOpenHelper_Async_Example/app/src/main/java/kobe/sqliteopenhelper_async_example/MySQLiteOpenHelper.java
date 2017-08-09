package kobe.sqliteopenhelper_async_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kobe on 07/08/2017.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "KKD";
    private static final int DB_VERSION = 1;
    private static MySQLiteOpenHelper mInstance = null;

    synchronized public static MySQLiteOpenHelper getInstance(@NonNull final Context context) {
        if (mInstance == null) {
            mInstance = new MySQLiteOpenHelper(context);
        }

        return mInstance;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private Context mContext;
    private final String CREATE_DATABASE;

    private MySQLiteOpenHelper(Context context) {
        super(context, MyContract.TABLE_NAME + ".db", null, DB_VERSION);

        mContext = context;
        CREATE_DATABASE = "CREATE TABLE " + MyContract.TABLE_NAME
                + "(" + MyContract.COL_ID + " INTEGER PRIMARY KEY, "
                + MyContract.COL_DATE + " DATETIME NOT NULL, "
                + MyContract.COL_INFO + " VARCHAR, "
                + MyContract.COL_AMOUNT + " INTEGER)";
    }

    public void release() {
        mContext = null;
    }

    //>>>>>> extends SQLiteOpenHelper

    //>> if database is not existed, this method will be called
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
        insertDefaultData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

    }

    //<<<<<<

    private void insertDefaultData(SQLiteDatabase db) {
        InputStream is    = mContext.getResources().openRawResource(R.raw.expenses);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb  = new StringBuilder();

        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }

            //>> parse json
            JSONObject jsonObj = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObj.getJSONArray("expenses");

            JSONObject exp;
            final int len = jsonArray.length();
            for(int i = 0; i < len; i++) {
                exp = jsonArray.getJSONObject(i);
                ContentValues values = new ContentValues();
                values.put(MyContract.COL_DATE, exp.optString(MyContract.COL_DATE));
                values.put(MyContract.COL_INFO, exp.optString(MyContract.COL_INFO));
                values.put(MyContract.COL_AMOUNT, exp.optInt(MyContract.COL_AMOUNT));

                //>> use AddItemService to insert data in worker thread
                AddItemService.addItem(mContext, values, (i + 1) == len);
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
