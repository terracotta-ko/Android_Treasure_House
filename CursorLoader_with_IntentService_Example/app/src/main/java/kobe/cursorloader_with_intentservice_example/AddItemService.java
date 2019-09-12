package kobe.cursorloader_with_intentservice_example;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class AddItemService extends IntentService {
    private final static String sITEM = "ITEM";
    private final static String sLAST = "LAST";
    public static final String BROADCAST_UPDATED = "UPDATED";
    private static final String TAG = "KKD";

    public static void addItem(@NonNull final Context context, @NonNull ContentValues values, boolean isLast) {
        Intent intent = new Intent(context, AddItemService.class);
        intent.setAction(Intent.ACTION_INSERT);
        intent.putExtra(sITEM, values);
        intent.putExtra(sLAST, isLast);
        context.startService(intent);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private MySQLiteOpenHelper mMySQLiteOpenHelper = null;

    public AddItemService() {
        super("AddItemService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null && intent.getAction() == Intent.ACTION_INSERT) {
            if (mMySQLiteOpenHelper == null) {
                mMySQLiteOpenHelper = MySQLiteOpenHelper.getInstance(getApplicationContext());
            }

            long id =  mMySQLiteOpenHelper.getWritableDatabase().insert(
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
}
