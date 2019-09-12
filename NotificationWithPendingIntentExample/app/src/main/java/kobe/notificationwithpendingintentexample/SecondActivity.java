package kobe.notificationwithpendingintentexample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by kobe on 19/05/2017.
 */

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //>> only show different layout
        setContentView(R.layout.activity_second);
    }
}
