package kobe.sqliteopenhelper_with_intentservice_example;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AddItemService extends IntentService {

    public static final String BROADCAST_UPDATED = "UPDATED";
    private static final String sITEM = "ITEM";
    private static final String sLAST = "LAST";
    private static final String TAG = "KKD";

    public static void addItem(final Context context, final ContentValues contentValues, final boolean isLast) {
        Intent intent = new Intent(context, AddItemService.class);
        intent.setAction(Intent.ACTION_INSERT);
        intent.putExtra(sITEM, contentValues);
        intent.putExtra(sLAST, isLast);
        context.startService(intent);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private MySQLiteOpenHelper mMySQLiteOpenHelper = null;

    public AddItemService() {
        super("AddItemService");
    }

    //>>>>>> extends IntentService

    @Override
    protected void onHandleIntent(Intent intent) {
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
                Log.e(TAG, "[AddItemService] onHandleIntent: insert failed");
            }

            if (intent.getBooleanExtra(sLAST, true)) {
                sendBroadcast(new Intent(BROADCAST_UPDATED));
            }
        }
    }

    //<<<<<<
}
