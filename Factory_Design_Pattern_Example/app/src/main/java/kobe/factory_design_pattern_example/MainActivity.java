package kobe.factory_design_pattern_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Animal mAnimal = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onDogClick(View view) {
        mAnimal = AnimalFactory.produce(AnimalFactory.DOG);
        findViewById(R.id.btn_msg).setEnabled(true);
    }

    public void onCatClick(View view) {
        mAnimal = AnimalFactory.produce(AnimalFactory.CAT);
        findViewById(R.id.btn_msg).setEnabled(true);
    }

    public void onMsgClick(View view) {
        TextView textView = (TextView) findViewById(R.id.tv_msg);

        if (mAnimal != null) {
            StringBuilder sb = new StringBuilder(mAnimal.showMsg());
            sb.append(" and ");

            if (mAnimal instanceof Dog) {
                sb.append(((Dog) mAnimal).eatBones());
            }
            else {
                sb.append(((Cat) mAnimal).eatFish());
            }

            textView.setText(sb.toString());
        }
    }

    public void onClearClick(View view) {
        mAnimal = null;
        findViewById(R.id.btn_msg).setEnabled(false);
        ((TextView) findViewById(R.id.tv_msg)).setText(R.string.no_animal);
    }
}
