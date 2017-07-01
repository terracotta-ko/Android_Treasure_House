package ko.standaloneserviceexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartServiceClick(View view) {
        Intent intent = new Intent(this, MyService.class);

        //>> the service won't be bound with MainActivity
        startService(intent);
    }

    //>> should see the Log :  D/MyService: onDestroy
    public void onStopServiceClick(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
}
