package ko.intentserviceexample;

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

    public void onStartClick(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra(MyIntentService.PARAM_MSG, "TASK_1");
        startService(intent);

        intent.putExtra(MyIntentService.PARAM_MSG, "TASK_2");
        startService(intent);
    }
}
