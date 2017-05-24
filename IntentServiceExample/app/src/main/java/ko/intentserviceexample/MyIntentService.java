package ko.intentserviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class MyIntentService extends IntentService {
    public static final String PARAM_MSG = "message";
    private final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String msg = intent.getStringExtra(PARAM_MSG);
            Log.d(TAG, "onHandleIntent");
            SystemClock.sleep(3000);    //>> wait for 3 secs

            String time = DateFormat.format("hh:mm:ss", System.currentTimeMillis()) + "\t" + msg;
            Log.d(TAG, time);
        }
    }
}
