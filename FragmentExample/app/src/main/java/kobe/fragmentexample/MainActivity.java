package kobe.fragmentexample;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ContentFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //>> load Fragment dynamically
        //>> if you don't want load Fragment dynamically
        //>> you can use fragment tag in the layout XML
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_place, ContentFragment.newInstance("a","b"))
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
