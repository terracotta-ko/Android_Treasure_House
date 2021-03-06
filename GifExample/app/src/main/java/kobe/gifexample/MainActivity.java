package kobe.gifexample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //>> use library android-gif-drawable to show GIF
        //>> https://github.com/koral--/android-gif-drawable
        GifImageView imageView = (GifImageView) findViewById(R.id.gifimage);

        try {
            //>> get GIF
            GifDrawable gifFromResource = new GifDrawable(getResources(), R.drawable.birdfood);
            imageView.setImageDrawable(gifFromResource);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
