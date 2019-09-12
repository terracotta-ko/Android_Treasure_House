package kobe.sqliteopenhelperexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kobe on 30/07/2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String TAG = "KKD";
    private static MySQLiteHelper mInstance = null;

    synchronized public static MySQLiteHelper getInstance(@NonNull Context context) {
        if (mInstance == null) {
            mInstance = new MySQLiteHelper(context);
        }

        return mInstance;
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private Context mContext;
    private final String CREATE_DATABASE;

    public MySQLiteHelper(Context context) {
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

    //>> if extant database's version is older, this method will be called
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //<<<<<<

    private void insertDefaultData(SQLiteDatabase db) {
        InputStream ins = mContext.getResources().openRawResource(R.raw.expenses);
        BufferedReader br = new BufferedReader(new InputStreamReader(ins));
        StringBuilder sb = new StringBuilder();

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
                values.put(MyContract.COL_DATE, exp.getString(MyContract.COL_DATE));
                values.put(MyContract.COL_INFO, exp.getString(MyContract.COL_INFO));
                values.put(MyContract.COL_AMOUNT, exp.getInt(MyContract.COL_AMOUNT));

                long id = db.insert(MyContract.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e(TAG, "insert failed");
                }
            }
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
