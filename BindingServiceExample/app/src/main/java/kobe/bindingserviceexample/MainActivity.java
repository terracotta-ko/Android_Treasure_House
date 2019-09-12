package kobe.bindingserviceexample;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private final String TAG = "MainActivity";
    MyBindingService mBindingService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //>> bind service
        Intent intent = new Intent(this, MyBindingService.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        //>> unbind service
        unbindService(this);
        super.onPause();
    }

    //>>>>>> implement ServiceConnection

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

    //<<<<<<

    public void onSendClick(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_text);
        String msg = editText.getText().toString();

        if (mBindingService != null) {
            mBindingService.sendMsg(msg);
        }
    }
}
