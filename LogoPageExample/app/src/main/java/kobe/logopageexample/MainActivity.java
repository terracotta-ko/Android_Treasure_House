package kobe.logopageexample;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_page);
        getSupportActionBar().hide();

        //>> display the logo page for 3 secs
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //>> change to different layout
                setContentView(R.layout.activity_main);
                getSupportActionBar().show();
            }
        }.start();
    }
}
