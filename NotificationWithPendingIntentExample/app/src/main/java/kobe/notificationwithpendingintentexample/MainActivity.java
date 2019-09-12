package kobe.notificationwithpendingintentexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeNotification();
    }

    private void makeNotification() {
        Intent intent = new Intent(this, SecondActivity.class);

        /*
        //>> method 1
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_ONE_SHOT);

        */

        //>> method 2
        //>> TaskStack will make sure that user will return to MainActivity
        //>> when user press back-button in the SecondActivity
        //>> NOTICE: need to add android:parentActivityName=".MainActivity" property to SecondActivity
        //>> in the manifest
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SecondActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent= stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);

        //>> setup Notification
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)   //>> must have icon
                .setTicker("This is Ticker")
                .setContentTitle("This is Title")
                .setContentText("This is Text")
                .setContentInfo("This is Info")
                .setContentIntent(pendingIntent)
                .build();

        //>> use NotificationManager to notify
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }
}
