package kobe.bindingserviceexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyBindingService extends Service {
    private final String TAG = "MyBindingService";
    private MyBinder mBinder = new MyBinder();

    //>> inner class which is inherited from Binder
    public class MyBinder extends Binder {
        public MyBindingService getService() {
            return MyBindingService.this;
        }
    }

    public MyBindingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    public void sendMsg(String msg) {
        Log.d(TAG, "sendMsg: " + msg);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
