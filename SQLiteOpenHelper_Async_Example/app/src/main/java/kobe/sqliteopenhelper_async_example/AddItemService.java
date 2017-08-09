package kobe.sqliteopenhelper_async_example;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by kobe on 07/08/2017.
 */

public class AddItemService extends IntentService {

    public static final String BROADCAST_UPDATED = "UPDATED";
    private static final String sITEM = "ITEM";
    private static final String sLAST = "LAST";
    private static final String TAG = "KKD";

    public static void addItem(Context context, ContentValues contentValues, boolean isLast) {
        Intent intent = new Intent(context, AddItemService.class);
        intent.setAction(Intent.ACTION_INSERT);
        intent.putExtra(sITEM, contentValues);
        intent.putExtra(sLAST, isLast);
        context.startService(intent);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private MySQLiteOpenHelper mMySQLiteOpenHelper = null;

    public AddItemService() {
        super("AddItemService");
    }

    //>>>>>> extends IntentService

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction() == Intent.ACTION_INSERT) {
            if (mMySQLiteOpenHelper == null) {
                mMySQLiteOpenHelper = MySQLiteOpenHelper.getInstance(getApplicationContext());
            }

            long id = mMySQLiteOpenHelper.getWritableDatabase().insert(
                    MyContract.TABLE_NAME,
                    null,
                    (ContentValues) intent.getParcelableExtra(sITEM)
            );

            if (id == -1) {
                Log.d(TAG, "onHandleIntent: insert failed");
            }

            if (intent.getBooleanExtra(sLAST, true)) {
                sendBroadcast(new Intent(BROADCAST_UPDATED));
            }
        }
    }

    //<<<<<<
}
