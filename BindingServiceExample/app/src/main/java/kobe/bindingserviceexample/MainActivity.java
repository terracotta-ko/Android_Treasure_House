package kobe.bindingserviceexample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private final String TAG = "MainActivity";
    MyBindingService mBindingService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onServiceConnected");
        MyBindingService.MyBinder binder = (MyBindingService.MyBinder) service;
        mBindingService = binder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG, "onServiceDisconnected");
        mBindingService = null;
    }

    public void onSendClick(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_text);
        String msg = editText.getText().toString();

        if (mBindingService != null) {
            mBindingService.sendMsg(msg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, MyBindingService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        unbindService(this);
        super.onPause();
    }
}
