package ko.intentserviceexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartClick(View view) {
        //>> IntentService can handle task asynchronously in different thread
        //>> and all the tasks will be put in a queue and execute in sequences
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra(MyIntentService.PARAM_MSG, "TASK_1");
        startService(intent);

        intent.putExtra(MyIntentService.PARAM_MSG, "TASK_2");
        startService(intent);
    }
}
