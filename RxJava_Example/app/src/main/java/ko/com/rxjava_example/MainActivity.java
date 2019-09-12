package ko.com.rxjava_example;

import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    
    public static final String TAG = "KKD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final TextView tvContent = findViewById(R.id.tv_content);

        //>> trigger every 1 secs and go on forever
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvContent.setText(String.valueOf(aLong));
                    }
                });
    }
}
